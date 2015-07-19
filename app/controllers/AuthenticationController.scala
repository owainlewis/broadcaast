package controllers

import models.User
import play.api.mvc._

object AuthenticationController extends SessionController with Forms {

  def login = Action { implicit request =>
    Ok(views.html.authentication.login(loginForm, getCurrentUser(request)))
  }

  def authenticate = Action { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => BadRequest(views.html.authentication.login(formWithErrors, getCurrentUser(request))),
      user => {
        if (User.authenticate(user.email, user.password)) {
          Redirect(routes.HomeController.index()).withSession("email" -> user.email).flashing(
            "success" -> "Welcome back")
        } else {
          BadRequest(views.html.authentication.login(loginForm, getCurrentUser(request))).flashing(
            "error" -> "Invalid email or password")
        }
      }
    )
  }

  def logout = Action {
    Redirect(routes.AuthenticationController.login()).withNewSession.flashing(
      "success" -> "You have been successfully logged out"
    )
  }
}
