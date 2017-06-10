package com.jactravel.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.jactravel.databases.Tables
import com.jactravel.databases.entity.SearchCountRecord
import com.jactravel.forms.DurationForm
import com.jactravel.utils.JsonSupport

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 07.06.17.
  */
trait CustomRoutes extends JsonSupport {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val executionContext: ExecutionContextExecutor

  /**
    * ClientSearch operation routes
    */
  private val clientSearchRoutes: Route = {
    pathSingleSlash {
      get {
        complete(HttpResponse(entity = HttpEntity("test connection")))
      }
    }
  }

  /**
    * SearchCount operation routes
    */
  private val searchCountRoutes: Route = {
    path("request" / "count") {
      get {
        entity(as[DurationForm]) { d =>
          onSuccess(Tables.searchCountTable.get(d.from, d.to)) {
            lst: List[SearchCountRecord] => complete(lst)
          }
        }
      }
    }
  }

  final val combinedRoutes: Route = clientSearchRoutes ~ searchCountRoutes
}
