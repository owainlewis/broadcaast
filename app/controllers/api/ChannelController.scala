package controllers.api

import play.api.mvc.{ Controller, Action }
import models._
import play.api.libs.json._
import repository._

sealed case class ChannelAggregate(stream: Channel, comments: Int)

sealed case class ChannelPartial(title: String, overview: Option[String])

object ChannelController extends Controller with TokenAuthentication {

  implicit val aggregateFormats = Json.format[ChannelAggregate]

  implicit val discussionPartialFormats = Json.format[ChannelPartial]

  private def insertAndReturn(user: Long, partial: ChannelPartial) = {

  }

  def index(user: Long) = Action {
    Ok(Json.toJson(ChannelRepository.all(user)))
  }

  def show(user: Long, id: Long) =  Action {
    ChannelRepository.findByUserAndId(user, id) map { c =>
      val comments = ItemRepository.all(id)
      Ok(Json.toJson(ChannelAggregate(c, comments.length)))
    } getOrElse NotFound.as("application/json")
  }

  def create(user: Long) = Action { request =>
    request.body.asJson.map { _.asOpt[ChannelPartial].map { partial =>
        Created("Ok").as("application/json")
      }.getOrElse {
        BadRequest("Invalid").as("application/json")
      }
    } getOrElse BadRequest
  }
}
