package com.jactravel.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.databases.entity.DayStatisticsRecord
import com.jactravel.utils.{Constants, JsonSupport}
import org.joda.time.DateTime

/**
  * Created by fayaz on 12.06.17.
  */
trait DaysStatisticsRoutes extends JsonSupport {

  /**
    * DaysStatistics operations routes
    */
  final val daysStatisticsRoutes: Route = {
    path("count" / "day" / "search") {
      get {
        parameter(
          'date.as[String] ? DateTime.now().toString(Constants.DAY_PATTERN),
          'from.as[Int],
          'to.as[Int],
          'interval.as[Int]
        ) { (date, from, to, interval) =>
          onSuccess(Tables.daysTable.get(date, from, to)) {
            case Nil => complete(StatusCodes.NoContent)
            case lst: List[(Int, Long)] =>
              val resLst = scala.collection.mutable.ArrayBuffer[(Int, Long)]()

              for (i <- from to to by interval) {
                resLst += i -> lst.filter(pair => pair._1 < i + interval && pair._1 > i).map(_._2).sum
              }

              complete(resLst.toList)
          }
        }
      }
    } ~
    path("count" / "hours" / "search" / "all") {
      get {
        parameter('date.as[String] ? DateTime.now.toString(Constants.DAY_PATTERN), 'limit.as[Int] ? 100) { (date, limit) =>
          onSuccess(Tables.daysTable.getAll(date, limit)) {
            case Nil => complete(StatusCodes.NoContent)
            case lst: List[DayStatisticsRecord] => complete(lst)
          }
        }
      }
    }
  }
}
