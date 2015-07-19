package io.forward.util

import org.joda.time._
import org.joda.time.format.PeriodFormat

object HumanDate {

  /** Remove milliseconds */
  private val periodType =
    PeriodType.standard().withMillisRemoved

  /**
   * Given a date time, returns the time ago in words i.e 2 minutes ago
   *
   * @param date A DateTime object
   * @return S
   */
  def timeAgoInWords(date: DateTime): Option[String] = {
    PeriodFormat.getDefault.print(new Period(date, new DateTime, periodType))
      .split(',').headOption flatMap (_.split("and").headOption) map (_ ++ " ago")
  }
}
