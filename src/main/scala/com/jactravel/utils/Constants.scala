package com.jactravel.utils

/**
  * Created by fayaz on 12.06.17.
  */
object Constants {

  /**
    * Date Constants
    */
  final val FULL_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"

  /**
    * Booking Constants
    */
  final val BOOKING_TODAY = "booking_today"
  final val BOOKING_YESTERDAY = "booking_yesterday"
  final val BOOKING_LAST_WEEK = "booking_last_week"
  final val BOOKING_LAST_YEAR = "booking_last_year"

  /**
    * Proxy Query Constants
    */
  final val SEARCHES_TODAY = "searches_today"
  final val SEARCHES_YESTERDAY = "searches_yesterday"
  final val SEARCHES_LAST_WEEK = "searches_last_week"
  final val SEARCHES_LAST_YEAR = "searches_last_year"

  /**
    * Time Markers
    */
  sealed trait Targets
  case object Today extends Targets
  case object Yesterday extends Targets
  case object LastWeek extends Targets
  case object LastYear extends Targets

  /**
    * Unit of time in ms
    */
  final val dayInMillis: Long = 86400000L
  final val weekInMillis: Long = dayInMillis * 7
  def yearInMillis(leap: Boolean): Long = if (leap) dayInMillis * 366 else dayInMillis * 365

}
