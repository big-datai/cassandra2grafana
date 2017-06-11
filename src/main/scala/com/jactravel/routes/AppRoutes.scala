package com.jactravel.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 07.06.17.
  */
trait AppRoutes extends HourStatisticsRoutes with ClientSearchRoutes {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val executionContext: ExecutionContextExecutor

  final val applicationRoutes: Route = {
    pathSingleSlash {
      get {
        complete("Test connection")
      }
    }
  }

  final val combinedRoutes: Route = applicationRoutes ~ hourStatisticsRoutes
}
