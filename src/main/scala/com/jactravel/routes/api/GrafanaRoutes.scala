package com.jactravel.routes.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.routes.forms.{PostForm, TargetForm}
import com.jactravel.utils.Constants._
import com.jactravel.utils.DefaultLogging.log
import com.jactravel.utils.JsonSupport
import com.jactravel.utils.RoutesHelper.spreadTimeSeries
import org.joda.time.DateTime
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by fayaz on 18.06.17.
  */
trait GrafanaRoutes extends JsonSupport {

  final val grafanaRoutes: Route = grafana

  def grafana: Route = {
    post {
      path("query") {
        entity(as[PostForm]) { form =>
          val interval = form.intervalMs
          val from = form.range.from
          val to = form.range.to
          val res = form.targets map {

            case TargetForm(SEARCHES_TODAY, _, _) => // Today target calculus
              Tables.queryProxyRequestTable.getSearchesCountByTime(from, to) map {
                case lst: List[DateTime] =>
                  log.info(s"retrieved non empty list for $SEARCHES_TODAY")
                  JsObject(
                    "target" -> SEARCHES_TODAY,
                    "datapoints" -> spreadTimeSeries(lst, from, to, interval).toJson
                  )
                case _ =>
                  log.info(s"empty list returned for $SEARCHES_TODAY with params $from, $to")
                  JsObject.empty
              }

            case TargetForm(SEARCHES_YESTERDAY, _, _) => // Yesterday target calculus
              Tables.queryProxyRequestTable.getSearchesCountByTime(from.minusDays(1), to.minusDays(1)) map {
                case lst: List[DateTime] =>
                  log.info(s"retrieved non empty list for $SEARCHES_YESTERDAY")
                  JsObject(
                    "target" -> SEARCHES_YESTERDAY,
                    "datapoints" -> spreadTimeSeries(lst, from, to, interval, Yesterday).toJson
                  )
                case _ =>
                  log.info(s"empty list returned for $SEARCHES_YESTERDAY with params $from, $to")
                  JsObject.empty
              }

            case TargetForm(SEARCHES_LAST_WEEK, _, _) => // Last week target calculus
              Tables.queryProxyRequestTable.getSearchesCountByTime(from.minusWeeks(1), to.minusWeeks(1)) map {
                case lst: List[DateTime] =>
                  log.info(s"retrieved non empty list for $SEARCHES_LAST_WEEK")
                  JsObject(
                    "target" -> SEARCHES_LAST_WEEK,
                    "datapoints" -> spreadTimeSeries(lst, from, to, interval, LastWeek).toJson
                  )
                case _ =>
                  log.info(s"empty list returned for $SEARCHES_LAST_WEEK with params $from, $to")
                  JsObject.empty
              }

            case TargetForm(SEARCHES_LAST_YEAR, _, _) => // Last year target calculus
              Tables.queryProxyRequestTable.getSearchesCountByTime(from.minusYears(1), to.minusYears(1)) map {
                case lst: List[DateTime] =>
                  log.info(s"retrieved non empty list for $SEARCHES_LAST_YEAR")
                  JsObject(
                    "target" -> SEARCHES_LAST_YEAR,
                    "datapoints" -> spreadTimeSeries(lst, from, to, interval, LastYear).toJson
                  )
                case _ =>
                  log.info(s"empty list returned for $SEARCHES_LAST_YEAR with params $from, $to")
                  JsObject.empty
              }

            case TargetForm(BOOKING_TODAY, _, _) => // Today target calculus
              Tables.bookingRequestTable.getBookingCountByTime(from, to) map {
                case lst: List[DateTime] =>
                  log.info(s"retrieved non empty list for $BOOKING_TODAY")
                  JsObject(
                    "target" -> BOOKING_TODAY,
                    "datapoints" -> spreadTimeSeries(lst, from, to, interval, LastYear).toJson
                  )
                case _ =>
                  log.info(s"empty list returned for $BOOKING_TODAY with params $from, $to")
                  JsObject.empty
              }

            case TargetForm(BOOKING_YESTERDAY, _, _) => // Yesterday target calculus
              Tables.bookingRequestTable.getBookingCountByTime(from.minusDays(1), to.minusDays(1)) map {
                case lst: List[DateTime] =>
                  log.info(s"retrieved non empty list for $BOOKING_YESTERDAY")
                  JsObject(
                    "target" -> BOOKING_YESTERDAY,
                    "datapoints" -> spreadTimeSeries(lst, from, to, interval, LastYear).toJson
                  )
                case _ =>
                  log.info(s"empty list returned for $BOOKING_YESTERDAY with params $from, $to")
                  JsObject.empty
              }

            case TargetForm(BOOKING_LAST_WEEK, _, _) => // Last week target calculus
              Tables.bookingRequestTable.getBookingCountByTime(from.minusWeeks(1), to.minusWeeks(1)) map {
                case lst: List[DateTime] =>
                  log.info(s"retrieved non empty list for $BOOKING_LAST_WEEK")
                  JsObject(
                    "target" -> BOOKING_LAST_WEEK,
                    "datapoints" -> spreadTimeSeries(lst, from, to, interval, LastYear).toJson
                  )
                case _ =>
                  log.info(s"empty list returned for $BOOKING_LAST_WEEK with params $from, $to")
                  JsObject.empty
              }

            case TargetForm(BOOKING_LAST_YEAR, _, _) => // Yesterday target calculus
              Tables.bookingRequestTable.getBookingCountByTime(from.minusDays(1), to.minusDays(1)) map {
                case lst: List[DateTime] =>
                  log.info(s"retrieved non empty list for $BOOKING_LAST_YEAR")
                  JsObject(
                    "target" -> BOOKING_LAST_YEAR,
                    "datapoints" -> spreadTimeSeries(lst, from, to, interval, LastYear).toJson
                  )
                case _ =>
                  log.info(s"empty list returned for $BOOKING_LAST_YEAR with params $from, $to")
                  JsObject.empty
              }

            case _ => throw new Exception("Unsupported target type received")
          }

          onSuccess(Future.sequence(res)) {
            lst: List[JsObject] => complete(lst)
          }
        }
      }
    }
  }
}
