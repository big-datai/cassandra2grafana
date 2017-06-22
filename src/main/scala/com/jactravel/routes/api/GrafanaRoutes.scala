package com.jactravel.routes.api

import akka.http.scaladsl.model.HttpMethods.POST
import akka.http.scaladsl.model._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import com.jactravel.databases.Tables
import com.jactravel.routes.forms.{GrafanaResponse, PostForm, TargetForm}
import com.jactravel.utils.Constants._
import com.jactravel.utils.JsonSupport
import com.jactravel.utils.RoutesHelper._
import com.jactravel.utils.Types.AsyncCall
import org.joda.time.DateTime
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by fayaz on 18.06.17.
  */
trait GrafanaRoutes extends JsonSupport {

  implicit val materializer: ActorMaterializer

  final val grafanaRoutes: AsyncCall = {
    case HttpRequest(POST, Uri.Path("/query"), _, entity, _) =>
      Unmarshal(entity.withContentType(ContentTypes.`application/json`)).to[PostForm] flatMap { form =>
        val interval = form.intervalMs
        val limit = form.maxDataPoints

        val searchesResult = form.targets map {

          // TODAY TARGET CALCULUS
          case TargetForm(SEARCHES_TODAY, _, _) =>
            val from = form.range.from
            val to = form.range.to

            Tables.queryProxyRequestTable.getSearchesCountByTime(from, to, limit) map {
              case Nil => GrafanaResponse(SEARCHES_TODAY)
              case lst: List[DateTime] => toGrafanaResult(SEARCHES_TODAY, lst, from, to, interval)
            }

          // YESTERDAY TARGET CALCULUS
          case TargetForm(SEARCHES_YESTERDAY, _, _) => // Yesterday target calculus
            val from = form.range.from.minusDays(1)
            val to = form.range.to.minusDays(1)

            Tables.queryProxyRequestTable.getSearchesCountByTime(from, to, limit) map {
              case Nil => GrafanaResponse(SEARCHES_YESTERDAY)
              case lst: List[DateTime] => toGrafanaResult(SEARCHES_YESTERDAY, lst, from, to, interval, Yesterday)
            }

          // LAST WEEK TARGET CALCULUS
          case TargetForm(SEARCHES_LAST_WEEK, _, _) => // Last week target calculus
            val from = form.range.from.minusWeeks(1)
            val to = form.range.to.minusWeeks(1)

            Tables.queryProxyRequestTable.getSearchesCountByTime(from, to, limit) map {
              case Nil => GrafanaResponse(SEARCHES_LAST_WEEK)
              case lst: List[DateTime] => toGrafanaResult(SEARCHES_LAST_WEEK, lst, from, to, interval, LastWeek)
            }

          // LAST YEAR TARGET CALCULUS
          case TargetForm(SEARCHES_LAST_YEAR, _, _) => // Last year target calculus
            val from = form.range.from.minusYears(1)
            val to = form.range.to.minusYears(1)

            Tables.queryProxyRequestTable.getSearchesCountByTime(from, to, limit) map {
              case Nil => GrafanaResponse(SEARCHES_LAST_YEAR)
              case lst: List[DateTime] => toGrafanaResult(SEARCHES_LAST_YEAR, lst, from, to, interval, LastYear)
            }

          // BOOKING TODAY TARGET CALCULUS
          case TargetForm(BOOKING_TODAY, _, _) => // Today target calculus
            val from = form.range.from
            val to = form.range.to

            Tables.bookingRequestTable.getBookingCountByTime(from, to, limit) map {
              case Nil => GrafanaResponse(BOOKING_TODAY)
              case lst: List[DateTime] => toGrafanaResult(BOOKING_TODAY, lst, from, to, interval)
            }

          // BOOKING YESTERDAY TARGET CALCULUS
          case TargetForm(BOOKING_YESTERDAY, _, _) => // Yesterday target calculus
            val from = form.range.from.minusDays(1)
            val to = form.range.to.minusDays(1)

            Tables.bookingRequestTable.getBookingCountByTime(from, to, limit) map {
              case Nil => GrafanaResponse(BOOKING_YESTERDAY)
              case lst: List[DateTime] => toGrafanaResult(BOOKING_YESTERDAY, lst, from, to, interval, Yesterday)
            }

          // BOOKING LAST WEEK CALCULUS
          case TargetForm(BOOKING_LAST_WEEK, _, _) => // Last week target calculus
            val from = form.range.from.minusWeeks(1)
            val to = form.range.to.minusWeeks(1)

            Tables.bookingRequestTable.getBookingCountByTime(from, to, limit) map {
              case Nil => GrafanaResponse(BOOKING_LAST_WEEK)
              case lst: List[DateTime] => toGrafanaResult(BOOKING_LAST_WEEK, lst, from, to, interval, LastWeek)
            }

          // BOOKING LAST YEAR CALCULUS
          case TargetForm(BOOKING_LAST_YEAR, _, _) => // Yesterday target calculus
            val from = form.range.from.minusYears(1)
            val to = form.range.to.minusYears(1)

            Tables.bookingRequestTable.getBookingCountByTime(from, to, limit) map {
              case Nil => GrafanaResponse(BOOKING_LAST_YEAR)
              case lst: List[DateTime] => toGrafanaResult(BOOKING_LAST_YEAR, lst, from, to, interval, LastYear)
            }

          // UNSUPPORTED TARGET TYPE
          case _ => throw new Exception("Unsupported target type received")
        }

        Future.sequence(searchesResult) map (lst =>
          HttpResponse(entity = HttpEntity(ContentType(MediaTypes.`application/json`), lst.toJson.toString())))
      }
  }
}
