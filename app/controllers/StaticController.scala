package controllers

import play.api.mvc._
import play.api.cache.Cache

object StaticController extends SessionController {

  import play.api.Play.current

  private val staticCacheTime = 60 * 60 * 60

  def faq = Action { implicit request =>
    Cache.getOrElse("static.faq", staticCacheTime) {
      Ok(views.html.static.faq(getCurrentUser(request)))
    }
  }

  def press = Action { implicit request =>
    Cache.getOrElse("static.press", staticCacheTime) {
      Ok(views.html.static.press(getCurrentUser(request)))
    }
  }
}
