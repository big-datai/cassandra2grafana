package com.jactravel.routes.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.routes.forms.{PostForm, TargetForm}
import com.jactravel.utils.Constants._
import com.jactravel.utils.DefaultLogging.log
import com.jactravel.utils.JsonSupport
import com.jactravel.utils.RoutesHelper._
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
          val res = form.targets map {

            case TargetForm(SEARCHES_TODAY, _, _) => // Today target calculus
              val from = form.range.from
              val to = form.range.to

              Tables.queryProxyRequestTable.getSearchesCountByTime(from, to) map {
                case Nil =>
                  log.info(s"empty list returned for $SEARCHES_TODAY with params $from, $to")
                  JsObject.empty
                case lst: List[DateTime] => toGrafanaResult(SEARCHES_TODAY, lst, from, to, interval)
              }

            case TargetForm(SEARCHES_YESTERDAY, _, _) => // Yesterday target calculus
              val from = form.range.from.minusDays(1)
              val to = form.range.to.minusDays(1)

              Tables.queryProxyRequestTable.getSearchesCountByTime(from, to) map {
                case Nil =>
                  log.info(s"empty list returned for $SEARCHES_YESTERDAY with params $from, $to")
                  JsObject.empty
                case lst: List[DateTime] => toGrafanaResult(SEARCHES_YESTERDAY, lst, from, to, interval, Yesterday)
              }

            case TargetForm(SEARCHES_LAST_WEEK, _, _) => // Last week target calculus
              val from = form.range.from.minusWeeks(1)
              val to = form.range.to.minusWeeks(1)

              Tables.queryProxyRequestTable.getSearchesCountByTime(from.minusWeeks(1), to.minusWeeks(1)) map {
                case Nil =>
                  log.info(s"empty list returned for $SEARCHES_LAST_WEEK with params $from, $to")
                  JsObject.empty
                case lst: List[DateTime] => toGrafanaResult(SEARCHES_LAST_WEEK, lst, from, to, interval, LastWeek)
              }

            case TargetForm(SEARCHES_LAST_YEAR, _, _) => // Last year target calculus
              val from = form.range.from.minusYears(1)
              val to = form.range.to.minusYears(1)

              Tables.queryProxyRequestTable.getSearchesCountByTime(from, to) map {
                case Nil =>
                  log.info(s"empty list returned for $SEARCHES_LAST_YEAR with params $from, $to")
                  JsObject.empty
                case lst: List[DateTime] => toGrafanaResult(SEARCHES_LAST_YEAR, lst, from, to, interval, LastYear)
              }

            case TargetForm(BOOKING_TODAY, _, _) => // Today target calculus
              val from = form.range.from
              val to = form.range.to

              Tables.bookingRequestTable.getBookingCountByTime(from, to) map {
                case Nil =>
                  log.info(s"empty list returned for $BOOKING_TODAY with params $from, $to")
                  JsObject.empty
                case lst: List[DateTime] => toGrafanaResult(BOOKING_TODAY, lst, from, to, interval)
              }

            case TargetForm(BOOKING_YESTERDAY, _, _) => // Yesterday target calculus
              val from = form.range.from.minusDays(1)
              val to = form.range.to.minusDays(1)

              Tables.bookingRequestTable.getBookingCountByTime(from, to) map {
                case Nil =>
                  log.info(s"empty list returned for $BOOKING_YESTERDAY with params $from, $to")
                  JsObject.empty
                case lst: List[DateTime] => toGrafanaResult(BOOKING_YESTERDAY, lst, from, to, interval, Yesterday)
              }

            case TargetForm(BOOKING_LAST_WEEK, _, _) => // Last week target calculus
              val from = form.range.from.minusWeeks(1)
              val to = form.range.to.minusWeeks(1)

              Tables.bookingRequestTable.getBookingCountByTime(from, to) map {
                case Nil =>
                  log.info(s"empty list returned for $BOOKING_LAST_WEEK with params $from, $to")
                  JsObject.empty
                case lst: List[DateTime] => toGrafanaResult(BOOKING_LAST_WEEK, lst, from, to, interval, LastWeek)
              }

            case TargetForm(BOOKING_LAST_YEAR, _, _) => // Yesterday target calculus
              val from = form.range.from.minusYears(1)
              val to = form.range.to.minusYears(1)

              Tables.bookingRequestTable.getBookingCountByTime(from, to) map {
                case Nil =>
                  log.info(s"empty list returned for $BOOKING_LAST_YEAR with params $from, $to")
                  JsObject.empty
                case lst: List[DateTime] => toGrafanaResult(BOOKING_LAST_YEAR, lst, from, to, interval, LastYear)
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
