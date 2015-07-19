package controllers

import controllers.ItemController._
import play.api.mvc._
import repository.ActionsRepository

object ActionController extends Controller {

  /**
   * User performs an action such as liking a comment or staring it etc
   *
   * We should only allow a user to perform any action once
   */
  def like(channel: Long, comment: Long) = withUser { user => { implicit request =>
    if (!ActionsRepository.userAlreadyLiked(comment, user.id)) {
      ActionsRepository.like(comment, user.id)
    }
    Redirect(routes.ChannelController.show(channel))
  }}
}
