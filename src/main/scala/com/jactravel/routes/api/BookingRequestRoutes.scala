package com.jactravel.routes.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.routes.RoutesHelper._
import com.jactravel.utils.DefaultLogging.log
import com.jactravel.utils.JsonSupport
import org.joda.time.DateTime

import scala.language.implicitConversions

/**
  * Created by fayaz on 14.06.17.
  */
trait BookingRequestRoutes extends JsonSupport {

  final val bookingRoutes: Route = getBookingCount

  def getBookingCount: Route = {
    get {
      path("query" / "count" / "booking") {
        parameter('from.as[String], 'to.as[String], 'interval.as[Int]) { (from, to, interval) =>
          log.info(s"Retrieve booking count request with params { from: $from, to: $to, interval: $interval seconds}")
          onSuccess(Tables.bookingRequestTable.getBookingCountByTime(from, to)) {
            case Nil =>
              log.info("Retrieved empty list from request")
              complete(StatusCodes.NoContent)
            case lst: List[DateTime] =>
              log.info(s"Retrieved non empty list from request")
              complete(spread(lst, from, to, interval * 1000))
          }
        }
      }
    }
  }
}
