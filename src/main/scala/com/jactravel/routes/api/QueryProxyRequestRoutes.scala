package com.jactravel.routes.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.utils.DefaultLogging.log
import com.jactravel.utils.JsonSupport
import com.jactravel.utils.RoutesHelper._
import org.joda.time.DateTime

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.implicitConversions
/**
  * Created by fayaz on 14.06.17.
  */
trait QueryProxyRequestRoutes extends JsonSupport {

  final val queryRoutes: Route = getSearchCount ~ getSuccessCount

  def getSearchCount: Route = {
    get {
      path("query" / "count" / "search") {
        parameter('from.as[String], 'to.as[String], 'interval.as[Int]) { (from, to, interval) =>
          log.info(s"Retrieve search count request with params { from: $from, to: $to, interval: $interval seconds}")
          onSuccess(Tables.queryProxyRequestTable.getSearchesCountByTime(from, to)) {
            case Nil =>
              log.info("Retrieved empty list from request")
              complete(StatusCodes.NoContent)
            case lst: List[DateTime] =>
              log.info(s"Retrieved non empty list from request")
              complete(spreadTimeSeries(lst, from, to, interval * 1000))
          }
        }
      }
    }
  }

  // Experimental
  def getSearchCountStreamly: Route = {
    get {
      path("query" / "count" / "search" / "streamly") {
        parameter('from.as[String], 'to.as[String], 'interval.as[Int]) { (from, to, interval) =>
          log.info(s"Retrieve search count request with params { from: $from, to: $to, interval: $interval seconds}")
          onSuccess(Tables.queryProxyRequestTable.getSearchesCountByTimeStreamly(
            from, to)) {
            case Nil =>
              log.info("Retrieved empty list from request")
              complete(StatusCodes.NoContent)
            case lst: List[DateTime] =>
              log.info(s"Retrieved non empty list from request")
              complete(spreadTimeSeries(lst, from, to, interval * 1000))
          }
        }
      }
    }
  }

  def getSuccessCount: Route = {
    get {
      path("query" / "count" / "success") {
        parameter('from.as[String], 'to.as[String], 'interval.as[Int]) { (from, to, interval) =>
          log.info(s"Success count request received with params { from: $from, to: $to, interval: $interval seconds}")
          onSuccess(Tables.queryProxyRequestTable.getSearchesSuccessCountByTime(from, to)) {
            case Nil =>
              log.info("Retrieved empty list from cassandra for request")
              complete(StatusCodes.NoContent)
            case lst: List[DateTime] =>
              log.info(s"Retrieved non empty list from cassandra for success count request")
              complete(spreadTimeSeries(lst, from, to, interval * 1000))
          }
        }
      }
    }
  }
}