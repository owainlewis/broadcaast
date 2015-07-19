package repository

import anorm.SqlParser._
import anorm._
import play.api.Play.current
import play.api.db.DB._
import models.CommentAction

object ActionsRepository extends Repository[CommentAction] {

  val tableName = "Actions"

  val rowParser: RowParser[CommentAction] = {
    get[Long]("item_id") ~
    get[Long]("user_id") ~
    get[String]("action_type") map { case comment ~ user ~ action =>
      CommentAction(comment, user, action)
    }
  }

  /**
   * Returns true if a user has already performed an action on a comment
   *
   * A given action can only be performed once
   *
   * @param comment The comment ID
   * @param user The user ID
   */
  def userAlreadyLiked(item: Long, user: Long) = withConnection { implicit c =>
    SQL("SELECT * FROM Actions WHERE item_id = {item} AND user_id = {user} LIMIT 1")
      .on('item -> item, 'user -> user)
      .as(rowParser.singleOpt).isDefined
  }

  /**
   * Utility method that likes a user comment
   */
  def like(item: Long, user: Long) = withConnection { implicit c =>
    SQL("INSERT INTO Actions (item_id, user_id, action_type) VALUES({item}, {user}, {action})")
      .on('item -> item, 'user -> user, 'action -> "like").executeInsert()
  }

  /**
   * Return a count of the number of actions for a given comment
   */
  def countFor(action: String, item: Long): Int = withConnection { implicit c =>
    SQL("SELECT COUNT(*) FROM Actions WHERE action_type = {action} AND item_id = {item} ")
      .on('action -> action, 'item -> item).as(SqlParser.scalar[Int].single)
  }
}
