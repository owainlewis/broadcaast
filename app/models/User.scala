package models
import java.security._
import java.math.BigInteger
import repository.{ChannelRepository, UserRepository}
import org.joda.time.DateTime
import security.Encryption
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class User(id: Long, username: String, email: String, password: String, token: String, created: DateTime) {

  def streams: Seq[Channel] = ChannelRepository.conversationsForUser(id)
}

case class UserPartial(id: Long, username: String, email: String)

object UserPartial {

  implicit val locationWrites: Writes[UserPartial] = (
    (__ \ "id").write[Long] and
    (__ \ "username").write[String] and
    (__ \ "email").write[String])(unlift(UserPartial.unapply))
}

object User {

  /**
   * Fetch the image URL for a given user email address
   *
   * @param email A user email address
   */
  def imageURL(email: String): String = {
    val hash = MessageDigest.getInstance("MD5").digest(email.getBytes("UTF-8"))
    val hashString = new BigInteger(1,hash).toString(16)
    s"//gravatar.com/avatar/$hashString?s=72&d=mm"
  }

  /**
   * Authenticate a user by comparing the un-hashed password with a hashed password in the database
   *
   * @param email User email
   * @param password User password
   */
  def authenticate(email: String, password: String): Boolean = {
    UserRepository.findOneByEmail(email) exists { user =>
      Encryption.checkPassword(password, user.password)
    }
  }
}
