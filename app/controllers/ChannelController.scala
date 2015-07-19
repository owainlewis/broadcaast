package controllers

import repository.{SubscriptionRepository, ItemRepository, ChannelRepository}
import play.api.mvc._
import security.Secured
import play.api.cache.Cache
import play.api.Play.current

object ChannelController extends SessionController with Forms with Secured {

  /**
   * Cache time in seconds
   */
  private val cacheTime = 60

  def index = Action { implicit request =>
      val newest = Cache.getOrElse("new-channels", cacheTime) { ChannelRepository.newItems() }
      Ok(views.html.channels.index(newest, getCurrentUser(request)))
  }

  def show(id: Long) = Action { implicit request =>
    ChannelRepository.findOneById(id) map { channel =>
      val comments = ItemRepository.all(channel.id)
      Ok(views.html.channels.show(channel, comments, itemForm, getCurrentUser(request)))
    } getOrElse NotFound
  }

  def newChannel = withUser { user => { implicit request =>
    Ok(views.html.channels.create(channelForm, getCurrentUser(request)))
  }}

  def create = withUser { user => { implicit request =>
    channelForm.bindFromRequest.fold(
      formWithErrors => {
        println(formWithErrors)
        BadRequest(views.html.channels.create(formWithErrors, getCurrentUser(request)))
      },
      channel => {
        val channelIdentifier: Option[Long] =
          ChannelRepository.insert(channel.title, channel.context, public = true, user.id, channel.locked)
        channelIdentifier map { id => Redirect(routes.ChannelController.show(id)) } getOrElse BadRequest
      }
    )
  }}

  // Channel actions

  /**
   * Subscribe a user to a given channel. For instance a user might want to subscribe to BBC/world-news
   *
   * @param channel The ID of the channel to subscribe to
   */
  def subscribe(channel: Long)= withUser { user => { implicit request =>
    SubscriptionRepository.subscribe(user.id, channel)
    Redirect(routes.ChannelController.show(channel))
      .flashing("success" -> "You successfully subscribed")
  }}
}
