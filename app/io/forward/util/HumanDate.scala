package io.forward.util

import org.joda.time._
import org.joda.time.format.PeriodFormat

object HumanDate {
  
  private val periodType =
    PeriodType.standard().withMillisRemoved

  def timeAgoInWords(date: DateTime): Option[String] = {
    PeriodFormat.getDefault.print(new Period(date, new DateTime, periodType))
      .split(',').headOption flatMap (_.split("and").headOption) map (_ ++ " ago")
  }
}
