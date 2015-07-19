package repository

import anorm.SqlParser._
import anorm._
import io.forward.util.Sanitizer
import models.User
import org.joda.time.DateTime
import play.api.db.DB._
import play.api.Play.current
import security.Encryption
import io.forward.util.Token.randomAccessToken

object UserRepository extends Repository[User] {

  val tableName = "Users"

  val rowParser: RowParser[User] = {
    get[Long]("id") ~
    get[String]("username") ~
    get[String]("email") ~
    get[String]("password") ~
    get[String]("token") ~
    get[DateTime]("created") map {
      case id ~ username ~ email ~ password ~ token ~ created => User(id, username, email, password, token, created)
    }
  }

  // Query
  // *******************************************************************

  def all(limit: Int = 100): Seq[User] =
    withConnection { implicit c =>
      SQL(s"SELECT * FROM $tableName ORDER BY id DESC LIMIT $limit").as(rowParser.*)
    }

  def findOneByToken(token: String): Option[User] =
    withConnection { implicit c =>
      SQL(s"SELECT * FROM $tableName WHERE token = {token}")
        .on('token -> token)
        .as(rowParser.singleOpt)
    }

  def findOneByUsername(username: String): Option[User] =
    withConnection { implicit c =>
      SQL(s"SELECT * FROM $tableName WHERE username = {username}")
        .on('username -> username)
        .as(rowParser.singleOpt)
    }

  def findOneByEmail(email: String): Option[User] = withConnection { implicit c =>
    SQL(s"SELECT * FROM $tableName WHERE email = {email}")
      .on('email -> Sanitizer.lowerCaseStrip(email))
      .as(rowParser.singleOpt)
  }

  // Command
  // *******************************************************************

  def refreshToken(userID: Long): Boolean = withConnection { implicit c =>
    SQL("UPDATE users SET token = {token} WHERE id = {id}").on(
      'token -> randomAccessToken,
      'id    -> userID
    ).execute()
  }

  def insert(username: String, email: String, password: String): Option[Long] =
    withConnection { implicit c =>
      SQL(s"INSERT INTO $tableName (username, email, password, created) VALUES ({username}, {email}, {password}, {created})")
        .on(
          'username -> Sanitizer.lowerCaseStrip(username),
          'email    -> Sanitizer.lowerCaseStrip(email),
          'password -> Encryption.encryptPassword(password),
          'created  -> new DateTime
        ).executeInsert()
    }

  def updatePassword(id: Long, password: String): Boolean = withConnection { implicit c =>
    SQL("UPDATE users SET password = {password} WHERE id = {id}").on(
      'id       -> id,
      'password -> Encryption.encryptPassword(password)
    ).executeUpdate() == id
  }

  def updateUsername(id: Long, username: String): Boolean = withConnection { implicit c =>
    SQL("UPDATE users SET username = {username} WHERE id = {id}").on(
      'id    -> id,
      'username -> Sanitizer.lowerCaseStrip(username)
    ).executeUpdate() == id
  }

  def updateEmail(id: Long, email: String): Boolean = withConnection { implicit c =>
    SQL("UPDATE users SET email = {email} WHERE id = {id}").on(
      'id    -> id,
      'email -> Sanitizer.lowerCaseStrip(email)
    ).executeUpdate() == id
  }
}
