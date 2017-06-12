package com.jactravel.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.jactravel.utils.swagger.SwaggerDocService

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 07.06.17.
  */
trait AppRoutes extends HourStatisticsRoutes with DaysStatisticsRoutes {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val executionContext: ExecutionContextExecutor

  final val applicationRoutes: Route = {
    pathSingleSlash {
      get {
        complete("Test connection")
      }
    } ~ new SwaggerDocService().routes
  }

  final val combinedRoutes: Route = cors() {
    applicationRoutes ~ hourStatisticsRoutes ~ daysStatisticsRoutes
  }
}
