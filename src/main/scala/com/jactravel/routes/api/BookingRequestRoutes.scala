package com.jactravel.routes.api

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.routes.RoutesHelper._
import com.jactravel.routes.forms.PostForm
import com.jactravel.utils.Constants._
import com.jactravel.utils.DefaultLogging.log
import com.jactravel.utils.Extension.RichString
import com.jactravel.utils.JsonSupport
import org.joda.time.DateTime
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by fayaz on 14.06.17.
  */
trait BookingRequestRoutes extends JsonSupport {

  final val bookingRoutes: Route = getBookingCount ~ getBookingCountStreamly ~ grafanaBookingCount

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

  def getBookingCountStreamly: Route = {
    get {
      path("query" / "count" / "booking" / "streamly") {
        parameter('from.as[String], 'to.as[String], 'interval.as[Int]) { (from, to, interval) =>
          log.info(s"Retrieve booking count request with params { from: $from, to: $to, interval: $interval seconds}")
          onSuccess(Tables.bookingRequestTable.getBookingCountByTimeStreamly(from, to)) {
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

  def grafanaBookingCount: Route = {
    post {
      path("query") {
        entity(as[PostForm]) { form =>
          val from = form.range.from
          val to = form.range.to
          val interval = form.interval.toSeconds

          val res: Future[List[JsValue]] = Future.sequence(form.targets.map { tf =>
            tf.target match {
              case BOOKING_TODAY => Tables.bookingRequestTable.getBookingCountByTime(from, to) map {
                case lst: List[DateTime] =>
                  JsObject(
                    "target" -> BOOKING_TODAY,
                    "datapoints" -> spread(lst, from, to, interval).toJson
                  )
                case _ => JsObject.empty
              }

              case BOOKING_YESTERDAY =>
                val yesterdayFrom = from.minusDays(1)
                val yesterdayTo = to.minusDays(1)

                Tables.bookingRequestTable.getBookingCountByTime(yesterdayFrom, yesterdayTo) map {
                  case lst: List[DateTime] =>
                    JsObject(
                      "target" -> BOOKING_YESTERDAY,
                      "datapoints" -> spread(lst, yesterdayFrom, yesterdayTo, interval).toJson
                    )
                  case _ => JsObject.empty
              }

              case BOOKING_LAST_WEEK =>
                val lastWeekFrom = from.minusWeeks(1)
                val lastWeekTo = to.minusWeeks(1)

                Tables.bookingRequestTable.getBookingCountByTime(lastWeekFrom, lastWeekTo) map {
                  case lst: List[DateTime] =>
                    JsObject(
                      "target" -> BOOKING_LAST_WEEK,
                      "datapoints" -> spread(lst, lastWeekFrom, lastWeekTo, interval).toJson
                    )
                  case _ => JsObject.empty
                }

              case BOOKING_LAST_YEAR =>
                val lastYearFrom = from.minusDays(1)
                val lastYearTo = to.minusDays(1)

                Tables.bookingRequestTable.getBookingCountByTime(lastYearFrom, lastYearTo) map {
                  case lst: List[DateTime] =>
                    JsObject(
                      "target" -> BOOKING_LAST_YEAR,
                      "datapoints" -> spread(lst, lastYearFrom, lastYearTo, interval).toJson
                    )
                  case _ => JsObject.empty
                }
              case other => throw new MatchError(s"Unknown booking target type: $other")
            }
          })

          onSuccess(res) {
            case Nil => complete(StatusCodes.NoContent)
            case lst: List[JsObject] => complete(lst)
          }
        }
      }
    }
  }
}