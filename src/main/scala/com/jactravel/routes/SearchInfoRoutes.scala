package com.jactravel.routes

import javax.ws.rs.Path

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.jactravel.databases.Tables
import com.jactravel.databases.entity.SearchInfoRecord
import com.jactravel.utils.{Constants, JsonSupport}
import io.swagger.annotations._
import org.joda.time.DateTime

/**
  * Created by fayaz on 11.06.17.
  */
@Api(value = "/search", produces = "application/json")
@Path("/search")
trait SearchInfoRoutes extends JsonSupport {

  /**
    * Search Info operation routes
    */
  final val searchInfoRoutes: Route = getCount ~ getAll

  @Path("/count{date}{from}{to}{interval}")
  @ApiOperation(httpMethod = "GET", value = "Get count of searches per time interval", tags = Array("Search Info"))
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "date", value = "Searched hours Date in format yyyy-MMM-dd", required = false, dataType = "string", paramType = "path"),
    new ApiImplicitParam(name = "from", value = "start time", required = true, dataType = "integer", paramType = "path"),
    new ApiImplicitParam(name = "to", value = "end time", required = true, dataType = "integer", paramType = "path"),
    new ApiImplicitParam(name = "interval", value = "time interval", required = true, dataType = "integer", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "", response = classOf[(Int, Int)], responseContainer = "List")
  ))
  def getCount: Route = {
    get {
      path("search" / "count") {
        parameter(
          'date.as[String] ? DateTime.now.toString(Constants.FULL_DATE_PATTERN),
          'from.as[Int],
          'to.as[Int],
          'interval.as[Int]
        ) { (date, from, to, interval) =>
          onSuccess(Tables.searchInfo.getCount(date, from, to)) {
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

  @Path("/all")
  @ApiOperation(httpMethod = "GET", value = "Getting limited numbers of entity", tags = Array("Search Info"))
  @ApiImplicitParams(Array(
    new ApiImplicitParam(name = "date", value = "Searched hours date in format yyyy-MMM-dd", required = false, dataType = "string", paramType = "path"),
    new ApiImplicitParam(name = "limit", value = " limit of retrieved items", required = false, dataType = "int", paramType = "path")
  ))
  @ApiResponses(Array(
    new ApiResponse(code = 200, message = "", response = classOf[List[SearchInfoRecord]])
  ))
  def getAll: Route = {
    get {
      path("search" / "all") {
        parameter('date.as[String] ? DateTime.now.toString("yyyy-MMM-dd"), 'limit.as[Int] ? 100) { (date, limit) =>
          onSuccess(Tables.searchInfo.getAll(date, limit)) {
            case Nil => complete(StatusCodes.NoContent)
            case lst: List[SearchInfoRecord] => complete(lst)
          }
        }
      }
    }
  }
}
