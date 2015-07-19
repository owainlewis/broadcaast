package models.util

import java.util.UUID
import anorm.{TypeDoesNotMatch, Column, ToStatement}

trait UUIDImplicits {

  implicit def uuidToStatement: ToStatement[UUID] =
    new ToStatement[UUID] {
      def set(s: java.sql.PreparedStatement, index: Int, aValue: UUID): Unit =
        s.setObject(index, aValue)
    }

  implicit def rowToUUID: Column[UUID] = {
    Column.nonNull[UUID] { (value, meta) =>
      value match {
        case v: UUID => Right(v)
        case _       => Left(
          TypeDoesNotMatch(
            s"Cannot convert $value: ${value.asInstanceOf[AnyRef].getClass} to UUID for column ${meta.column}")
        )
      }
    }
  }
}

