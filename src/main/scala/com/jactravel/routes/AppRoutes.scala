package com.jactravel.routes

import akka.http.scaladsl.server.Route
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.jactravel.utils.swagger.SwaggerDocService

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 07.06.17.
  */
trait AppRoutes extends SearchInfoRoutes with SwaggerDocService {
  implicit val executionContext: ExecutionContextExecutor

  final val applicationRoutes: Route = {
    pathSingleSlash {
      get {
        complete("Test connection")
      }
    }
  }

  final val combinedRoutes: Route = cors() {
    routes ~ applicationRoutes ~ searchInfoRoutes
  }
}
