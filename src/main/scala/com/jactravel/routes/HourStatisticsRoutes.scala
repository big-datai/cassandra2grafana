package com.jactravel.routes

import javax.ws.rs.Path

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.databases.entity.HourStatisticsRecord
import com.jactravel.utils.{Constants, JsonSupport}
import io.swagger.annotations._
import org.joda.time.DateTime

/**
  * Created by fayaz on 11.06.17.
  */
@Api(value = "/hours", description = "Hours Api", produces = "application/json")
@Path("/hours")
trait HourStatisticsRoutes extends JsonSupport {

  /**
    * HoursStatistics operation routes
    */
  final val hourStatisticsRoutes: Route = getCount ~ getAllCount

  @Path("/count/search{date}{from}{to}{interval}")
  @ApiOperation(httpMethod = "GET", value = "")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "date", value = "Searched hours Date", required = false, dataType = "string", paramType = "path"),
    new ApiImplicitParam(name = "from", value = " start time", required = true, dataType = "int", paramType = "path"),
    new ApiImplicitParam(name = "date", value = "end time", required = true, dataType = "int", paramType = "path"),
    new ApiImplicitParam(name = "date", value = "time interval", required = true, dataType = "int", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "", response = classOf[List[(Int, Int)]])
  ))
  private def getCount = {
    get {
      path("count" / "search") {
        parameter(
          'date.as[String] ? DateTime.now.toString(Constants.HOUR_PATTERN),
          'from.as[Int],
          'to.as[Int],
          'interval.as[Int]
        ) { (date, from, to, interval) =>
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
    }
  }

  @Path("/count/search/all{date}{limit}")
  @ApiOperation(httpMethod = "GET", value = "")
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "date", value = "Searched hours Date", required = false, dataType = "string", paramType = "path"),
    new ApiImplicitParam(name = "limit", value = " limit of retrieved items", required = false, dataType = "int", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "", response = classOf[List[HourStatisticsRecord]])
  ))
  private def getAllCount = {
    get {
      path("count" / "search" / "all") {
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
