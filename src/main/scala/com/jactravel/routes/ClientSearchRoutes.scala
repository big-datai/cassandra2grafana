package com.jactravel.routes

import akka.http.scaladsl.model.{HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives.{complete, get, pathSingleSlash}
import akka.http.scaladsl.server.Route
import com.jactravel.utils.JsonSupport

/**
  * Created by fayaz on 11.06.17.
  */
trait ClientSearchRoutes extends JsonSupport {

  /**
    * ClientSearch operation routes
    */
  val clientSearchRoutes: Route = {
    pathSingleSlash {
      get {
        complete(HttpResponse(entity = HttpEntity("test connection")))
      }
    }
  }
}
