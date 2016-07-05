package models

import org.joda.time.DateTime
import play.api.libs.json._
import repository.{ActionsRepository, UserRepository}

case class Item(id: Long, channel: Long, user: Option[Long], body: Option[String], created: DateTime) {

  def getUser: Option[User] =
    user flatMap UserRepository.findOneById

  def userName: String =
    getUser map (_.username) getOrElse ""

  def userImage: Option[String] =
    getUser map { u => User.imageURL(u.email) }

  def likes: Int = ActionsRepository.countFor("like", id)
}

object Item {
  implicit val commentFormats = Json.format[Item]
}
