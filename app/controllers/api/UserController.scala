package controllers.api

import models.UserPartial
import play.api.libs.json.Json
import play.api.mvc._
import repository.UserRepository

object UserController extends Controller with TokenAuthentication {

  def index = Action {
    val users = UserRepository.all() map (user => UserPartial(user.id, user.username, user.email))
    Ok(Json.toJson(users))
  }

  def show(id: Long) = Action {
    UserRepository.findOneById(id) map { user =>
      val userPartial = UserPartial(user.id, user.username, user.email)
      Ok(Json.toJson(userPartial))
    } getOrElse NotFound
  }
}
