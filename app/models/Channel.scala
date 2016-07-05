package models

import repository.UserRepository
import io.forward.util.{HumanDate, TimestampImplicits}
import org.joda.time._
import play.api.libs.json._

case class Channel(
  id: Long,
  title: String,
  context: Option[String],
  public: Boolean, // A stream can be public or private (only invited users)
  creator: Long,
  created: DateTime = new DateTime) {

  def createdBy: Option[User] = UserRepository.findOneById(creator)
  def createdInWords: Option[String] = HumanDate.timeAgoInWords(created)
}

object Channel extends TimestampImplicits {
  implicit val conversationFormats = Json.format[Channel]
}
