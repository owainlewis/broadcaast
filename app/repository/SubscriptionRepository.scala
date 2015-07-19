package repository

import anorm.SqlParser._
import anorm._
import models.Item
import org.joda.time.DateTime
import play.api.db.DB._
import play.api.Play.current

object SubscriptionRepository {

  val TableName = "Subscribers"

  /**
   * Subscribe a user to a particular channel
   * @param user The user to subscribe
   * @param channel The channel to subscribe to
   */
  def subscribe(user: Long, channel: Long) = withConnection { implicit c =>
    SQL(s"INSERT INTO $TableName (channel_id, user_id) VALUES({channel}, {user})")
      .on('user -> user, 'channel -> channel).executeInsert()
  }

  /**
   * Unsubscribe a user from a particular channel
   *
   * @param user The user to subscribe
   * @param channel The channel to subscribe to
   */
  def unsubscribe(user: Long, channel: Long) = ()

}
