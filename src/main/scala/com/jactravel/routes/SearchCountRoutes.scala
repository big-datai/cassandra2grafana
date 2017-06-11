package com.jactravel.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.databases.entity.SearchCountRecord
import com.jactravel.utils.JsonSupport
import org.joda.time.DateTime

/**
  * Created by fayaz on 11.06.17.
  */
trait SearchCountRoutes extends JsonSupport {

  /**
    * SearchCount operation routes
    */
  val searchCountRoutes: Route = {
    path("request") {
      get {
        parameter('date.as[String] ? DateTime.now.toString("yyyy-MMM-dd"), 'from.as[Int], 'to.as[Int], 'interval.as[Int]) { (date, from, to, interval) =>
          onSuccess(Tables.searchCountTable.get(date, from, to)) {
            case Nil => complete(StatusCodes.NoContent)
            case lst: List[SearchCountRecord] =>
              val result = scala.collection.mutable.Map[Int, Int]()

              for (i <- from to to by interval) {
                result(i) = lst.filter(e => e.time < i + interval && e.time > i).map(_.count).sum
              }

              complete(result.toList)
          }
        }
      }
    }

    path("request" / "all") {
      get {
        parameter('date.as[String] ? DateTime.now.toString("yyyy-MMM-dd"), 'limit.as[Int]) { (date, limit) =>
          onSuccess(Tables.searchCountTable.getAll(date, limit)) {
            case Nil => complete(StatusCodes.NoContent)
            case lst: List[SearchCountRecord] => complete(lst)
          }
        }
      }
    }
  }
}
