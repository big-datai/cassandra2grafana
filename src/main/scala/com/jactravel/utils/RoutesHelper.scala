package com.jactravel.utils

import com.jactravel.utils.Constants._
import org.joda.time.DateTime

import scala.annotation.tailrec

/**
  * Created by fayaz on 16.06.17.
  */
object RoutesHelper {

  def spreadTimeSeries(lst: List[DateTime],
                       from: DateTime,
                       to: DateTime,
                       interval: Long,
                       targetsType: Targets = Today): List[(Int, Long)] = {

    val isLeap = from.minusYears(1).year().isLeap

    val addition: Long = targetsType match {
      case Today => 0L
      case Yesterday => dayInMillis
      case LastWeek => weekInMillis
      case LastYear => yearInMillis(isLeap)
    }

    @tailrec
    def spreadAcc(from: DateTime, resLst: List[(Int, Long)] = Nil): List[(Int, Long)] = {
      if (from isAfter to) resLst
      else {
        val key = lst.count(date => (date isBefore from.plus(interval)) && (date isAfter from))
        val value = from.plus(addition).getMillis
        spreadAcc(from.plus(interval), key -> value :: resLst)
      }
    }

    spreadAcc(from)
  }
}
