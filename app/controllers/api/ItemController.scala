package controllers.api

import play.api.mvc._
import repository.ItemRepository
import play.api.libs.json.Json

object ItemController extends Controller {

  def index(user: Long, stream: Long) = Action {
    val comments = ItemRepository.findByUserAndStream(user, stream)
    Ok(
      Json.toJson(comments)
    )
  }

  def create(user: Long, stream: Long) = Action {
    Ok
  }
}
