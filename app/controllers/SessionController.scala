package controllers

import play.api.mvc._
import models.User
import repository.UserRepository

trait SessionController extends Controller {

  def getCurrentUser(implicit request : RequestHeader): Option[User] =
    request.session.get("email").flatMap(UserRepository.findOneByEmail)
}
