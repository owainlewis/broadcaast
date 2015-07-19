package controllers

import play.api.mvc._

object HomeController extends SessionController with Forms {

  def index = Action { implicit request =>
    Ok(views.html.index(registrationForm, getCurrentUser(request)))
  }
}
