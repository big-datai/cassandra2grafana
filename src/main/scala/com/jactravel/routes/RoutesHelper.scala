package com.jactravel.routes

import com.jactravel.utils.Constants
import org.joda.time.DateTime

import scala.annotation.tailrec

/**
  * Created by fayaz on 16.06.17.
  */
object RoutesHelper {

  def spread(lst: List[DateTime],
                     from: DateTime,
                     to: DateTime,
                     interval: Long): List[(String, Int)] = {

    @tailrec
    def spreadAcc(from: DateTime, resLst: List[(String, Int)] = Nil): List[(String, Int)] = {
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
