package repository

import anorm._
import play.api.db.DB._

trait Repository[T] {

  import play.api.Play.current

  val tableName : String

  val rowParser: RowParser[T]

  def findOneById(id: Long): Option[T] = withConnection { implicit c =>
    SQL(s"SELECT * FROM $tableName WHERE id = {id}")
      .on('id -> id)
      .as(rowParser.singleOpt)
  }

  def deleteOneById(id: Long): Boolean = withConnection { implicit c =>
    SQL(s"DELETE from $tableName WHERE id = {id}").on('id -> id).execute()
  }
}
