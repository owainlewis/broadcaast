package controllers

import repository.{ActionsRepository, ItemRepository}
import play.api.mvc.Action
import security.Secured

object ItemController extends SessionController with Forms with Secured {

  def show(stream: Long, item: Long) = Action {
    ItemRepository.findOneByStreamAndId(stream, item) map { i =>
      Ok(views.html.items.show(i))
    } getOrElse NotFound
  }

  def delete(channel: Long, item: Long) = withUser { user => { implicit request =>
    ItemRepository.deleteOneById(item)
    Redirect(routes.ChannelController.show(channel))
  }}

  def create(id: Long) = Action { implicit request =>
    itemForm.bindFromRequest.fold(
      formWithErrors => {
        println(formWithErrors.errorsAsJson)
        Redirect(routes.ChannelController.show(id)).flashing (
          "error" -> "Message is too big"
        )
      },
      comment => {
        val currentUserID = getCurrentUser(request) map (_.id)
        // Prevent users from commenting if the channel prevents anonymous comments
        ItemRepository.insert(id, currentUserID, comment.body)
        Redirect(routes.ChannelController.show(id)).flashing(
          "success" -> "Item created"
        )
      }
    )
  }
}
