package io.forward.util

import java.sql.Timestamp
import play.api.libs.json._
import play.api.libs.functional.syntax._

trait TimestampImplicits {

  implicit val rds: Reads[Timestamp] = (__ \ "time").read[Long].map { long => new Timestamp(long) }

  implicit val wrs: Writes[Timestamp] = (__ \ "time").write[Long].contramap { (a: Timestamp) => a.getTime }
}
