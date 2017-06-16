package com.jactravel.routes

import com.jactravel.utils.Constants
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.annotation.tailrec

/**
  * Created by fayaz on 16.06.17.
  */
object RoutesHelper {

  type TimeSeries = (String, Int)

  implicit def str2date(time: String): DateTime = DateTimeFormat.forPattern(Constants.FULL_DATE_PATTERN).parseDateTime(time)

  def spread(lst: List[DateTime],
                     from: DateTime,
                     to: DateTime,
                     interval: Long): List[TimeSeries] = {

    @tailrec
    def spreadAcc(from: DateTime, resLst: List[TimeSeries] = Nil): List[TimeSeries] = {
      if (from isAfter to) resLst
      else {
        val key = from.toString(Constants.FULL_DATE_PATTERN)
        val value = lst.count(date => (date isBefore from.plus(interval)) && (date isAfter from))
        spreadAcc(from.plus(interval), key -> value :: resLst)
      }
    }

    spreadAcc(from)
  }
}
