package controllers

import play.api.mvc._
import repository.UserRepository
import security.Secured
import play.api.cache.Cache

object UserController extends SessionController with Secured with Forms {

  import play.api.Play.current

  def index = Action { implicit request =>
    val users = UserRepository.all()
    Ok(views.html.users.index(users, getCurrentUser(request)))
  }

  def show(username: String) = Action { implicit request =>
    Cache.getOrElse(s"$username-profile-page", 60) {
      UserRepository.findOneByUsername(username) map { user =>
        Ok(views.html.users.show(user, getCurrentUser(request)))
      } getOrElse NotFound(views.html.common.notfound())
    }
  }

  def edit = withUser { user => { implicit request =>
    Ok(views.html.users.edit(
      user,
      userProfileForm.fill(
        ProfileData(Some(user.username), Some(user.email), None)
      )
    ))
  }}

  def update = withUser { user => { implicit request =>
    userProfileForm.bindFromRequest.fold(
      formWithErrors => {
        Redirect(routes.UserController.edit()).flashing("error" -> "Could not update profile")
      },
      userProfile => {
        userProfile.username map { username => UserRepository.updateUsername(user.id, username)}
        userProfile.email map { email => UserRepository.updateEmail(user.id, email)}
        userProfile.password map { password => UserRepository.updatePassword(user.id, password)}
        Redirect(routes.UserController.edit())
      }
    )
  }}
}
