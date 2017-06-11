package com.jactravel.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.databases.entity.HourStatisticsRecord
import com.jactravel.utils.JsonSupport
import org.joda.time.DateTime

/**
  * Created by fayaz on 11.06.17.
  */
trait HourStatisticsRoutes extends JsonSupport {

  /**
    * SearchCount operation routes
    */
  final val hourStatisticsRoutes: Route = {
    path("count" / "day" / "search") {
      get {
        parameter('date.as[String] ? DateTime.now.toString("yyyy-MMM-dd"), 'from.as[Int], 'to.as[Int], 'interval.as[Int]) { (date, from, to, interval) =>
          onSuccess(Tables.hoursTable.getCount(date, from, to)) {
            case Nil => complete(StatusCodes.NoContent)
            case lst: List[(Int, Int)] =>
              val resLst = scala.collection.mutable.ArrayBuffer[(Int, Int)]()

              for (i <- from to to by interval) {
                resLst += i -> lst.filter(pair => pair._1 < i + interval && pair._1 > i).map(_._2).sum
              }

              complete(resLst.toList)
          }
        }
      }
    } ~
    path("count" / "day" / "search" / "all") {
      get {
        parameter('date.as[String] ? DateTime.now.toString("yyyy-MMM-dd"), 'limit.as[Int] ? 100) { (date, limit) =>
          onSuccess(Tables.hoursTable.getAll(date, limit)) {
            case Nil => complete(StatusCodes.NoContent)
            case lst: List[HourStatisticsRecord] => complete(lst)
          }
        }
      }
    }
  }
}
