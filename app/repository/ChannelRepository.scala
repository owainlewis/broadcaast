package repository

import anorm.SqlParser._
import anorm._
import models.Channel
import play.api.db.DB._
import play.api.Play.current
import org.joda.time._

object ChannelRepository extends Repository[Channel] {

  val tableName = "Channels"

  val rowParser: RowParser[Channel] = {
    get[Long]("id") ~
    get[String]("title") ~
    get[Option[String]]("context") ~
    get[Boolean]("isPublic") ~
    get[Boolean]("locked") ~
    get[Long]("creator") ~
    get[DateTime]("created") map {
      case id ~ title ~ context ~ public ~ locked ~ creator ~ created =>
        Channel(id, title, context, public, locked, creator, created)
    }
  }

  // QUERY

  def all(user: Long): Seq[Channel] = withConnection { implicit c =>
    SQL(s"SELECT * FROM $tableName WHERE creator = {user} ORDER BY id DESC")
      .on('user -> user)
      .as(rowParser.*)
  }
  
  def newItems(limit: Int = 20): Seq[Channel] = withConnection { implicit c =>
    SQL(s"SELECT * FROM $tableName ORDER BY id DESC LIMIT $limit").as(rowParser.*)
  }

  def conversationsForUser(userID: Long) = withConnection { implicit c =>
    SQL(s"SELECT * from $tableName WHERE creator = {id} ORDER BY id DESC")
      .on('id -> userID)
      .as(rowParser.*)
  }

  def findByUserAndId(user: Long, id: Long): Option[Channel] = withConnection { implicit c =>
    SQL(s"SELECT * FROM $tableName WHERE creator = {user} AND id = {id}")
    .on('user -> user, 'id -> id)
    .as(rowParser.singleOpt)
  }

  // COMMAND

  def insert(title: String, context: Option[String], public: Boolean, creatorID: Long, locked: Boolean = false): Option[Long] =
    withConnection { implicit c =>
      SQL(s"INSERT INTO $tableName (title, context, isPublic, locked, creator, created) VALUES ({title}, {context}, {public}, {locked}, {creator}, {created})").on(
        'title -> title,
        'context -> context,
        'public -> true,
        'locked -> locked,
        'creator -> creatorID,
        'created -> new DateTime
      ).executeInsert()
    }
}
