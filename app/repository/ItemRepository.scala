package repository

import anorm.SqlParser._
import anorm._
import models.Item
import org.joda.time.DateTime
import play.api.db.DB._
import play.api.Play.current

object ItemRepository extends Repository[Item] {

  val tableName = "Items"

  val rowParser: RowParser[Item] = {
    get[Long]("id") ~
    get[Long]("channel_id") ~
    get[Option[Long]]("user_id") ~
    get[Option[String]]("body") ~
    get[DateTime]("created") map {
      case id ~ stream ~ user ~ body ~ created =>
        Item(id, stream, user, body, created)
    }
  }

  def all(stream: Long): Seq[Item] = withConnection { implicit c =>
    SQL(s"SELECT * FROM $tableName WHERE channel_id = {stream} AND user_id IS NOT NULL ORDER BY id DESC")
      .on('stream -> stream)
      .as(rowParser.*)
  }

  def findOneByStreamAndId(stream: Long, id: Long): Option[Item] = withConnection { implicit c =>
    SQL(s"SELECT * FROM $tableName WHERE channel_id = {stream} and id = {id}")
      .on('stream -> stream, 'id -> id)
      .as(rowParser.singleOpt)
  }

  def findByUserAndStream(user: Long, stream: Long): Seq[Item] = withConnection { implicit c =>
    SQL(s"SELECT * FROM $tableName WHERE user_id = {user} AND channel_id = {stream} ORDER BY id ASC")
      .on('user -> user, 'stream -> stream)
      .as(rowParser.*)
  }

  def insert(discussionID: Long, userID: Option[Long], body: Option[String]): Option[Long] =
    withConnection { implicit c =>
      SQL(s"INSERT INTO $tableName (channel_id, user_id, body, created) VALUES ({stream}, {user}, {body}, {created})")
        .on(
          'stream -> discussionID,
          'user -> userID,
          'body -> body,
          'created -> new DateTime
        ).executeInsert()
    }
}
