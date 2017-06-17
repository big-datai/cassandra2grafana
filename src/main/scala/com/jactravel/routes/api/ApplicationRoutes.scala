package com.jactravel.routes.api

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route

/**
  * Created by fayaz on 13.06.17.
  */
trait ApplicationRoutes {

  /**
    * Other application routes
    */
  final val applicationRoutes: Route = {
    pathSingleSlash {
      get {
        complete("Test connection")
      }
    }
  }
}
