package controllers

import play.api.mvc._
import repository.UserRepository

object RegistrationController extends SessionController with Forms {

  def signUp = Action { implicit request =>
    Ok(views.html.registration.signup(registrationForm, getCurrentUser(request)))
  }

  def create = Action { implicit request =>
    registrationForm.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.registration.signup(formWithErrors, getCurrentUser(request)))
      },
      userData => {
        UserRepository.insert(userData.username, userData.email, userData.password) map UserRepository.refreshToken
        Redirect(routes.ChannelController.newChannel()).withSession("email" -> userData.email).flashing(
          "success" -> "Sign up successful"
        )
      }
    )
  }
}
