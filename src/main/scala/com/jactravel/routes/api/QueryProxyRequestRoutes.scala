package com.jactravel.routes.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.databases.entity.QueryProxyRequestRecord
import com.jactravel.utils.DefaultLogging.log
import com.jactravel.utils.{Constants, JsonSupport}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import scala.annotation.tailrec
import scala.language.implicitConversions
/**
  * Created by fayaz on 14.06.17.
  */
trait QueryProxyRequestRoutes extends JsonSupport {
  type TimeSeries = (String, Int)

  implicit def str2date(time: String): DateTime = DateTimeFormat.forPattern(Constants.FULL_DATE_PATTERN).parseDateTime(time)

  final val queryProxyRequestRoutes: Route = getSearchCount ~ getSuccessCount

  def getSearchCount: Route = {
    get {
      path("query" / "count" / "search") {
        parameter('from.as[String], 'to.as[String], 'interval.as[Int]) { (from, to, interval) =>
          log.info(s"Search count request received with params { from: $from, to: $to, interval: $interval seconds}")
          onSuccess(Tables.queryProxyRequestTable.getRecordByTime(from, to)) {
            case Nil =>
              log.info("Retrieved empty list from cassandra for request")
              complete(StatusCodes.NoContent)
            case lst: List[QueryProxyRequestRecord] =>
              log.info(s"Retrieved non empty list from cassandra for search count request")
              complete(spread(lst, from, to, interval))
          }
        }
      }
    }
  }

  def getSuccessCount: Route = {
    get {
      path("query" / "count" / "success") {
        parameter('from.as[String], 'to.as[String], 'interval.as[Int]) { (from, to, interval) =>
          onSuccess(Tables.queryProxyRequestTable.getRecordByTime(from, to)) {
            case Nil =>
              log.info("Retrieved empty list from cassandra for request")
              complete(StatusCodes.NoContent)
            case lst: List[QueryProxyRequestRecord] =>
              log.info(s"Retrieved non empty list from cassandra for success count request")
              complete(spreadWithCondition(lst, from, to, interval, (qr: QueryProxyRequestRecord) => qr.success == "1"))
          }
        }
      }
    }
  }

  private def spread(lst: List[QueryProxyRequestRecord],
                     from: DateTime,
                     to: DateTime,
                     interval: Long): List[TimeSeries] = {
    var startDate = from
    val msInterval = interval * 1000
    val resLst = scala.collection.mutable.ArrayBuffer[TimeSeries]()

    while (startDate isBefore to) {
      resLst += startDate.toString(Constants.FULL_DATE_PATTERN) -> lst.count(record =>
        (record.client_request_utc_timestamp isBefore startDate.plus(msInterval)) &&
        (record.client_request_utc_timestamp isAfter startDate))
      startDate = startDate.plus(msInterval)
    }

    resLst.toList
  }

  private def spreadWithCondition(lst: List[QueryProxyRequestRecord],
                                  from: DateTime,
                                  to: DateTime,
                                  interval: Long,
                                  semiCondition: QueryProxyRequestRecord => Boolean): List[TimeSeries] = {
    val msInterval = interval * 1000

    @tailrec
    def spreadAcc(from: DateTime, resLst: List[TimeSeries] = Nil): List[TimeSeries] = {
      if (from isAfter to) resLst
      else {
        val key = from.toString(Constants.FULL_DATE_PATTERN)
        val value = lst.count(record =>
          (record.client_request_utc_timestamp isBefore from.plus(msInterval)) &&
          (record.client_request_utc_timestamp isAfter from) &&
          semiCondition(record))
        spreadAcc(from.plus(msInterval), key -> value :: resLst)
      }
    }

    spreadAcc(from)
  }

}