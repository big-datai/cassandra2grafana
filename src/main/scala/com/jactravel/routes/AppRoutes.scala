package com.jactravel.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.Logger

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 07.06.17.
  */
trait AppRoutes extends SearchCountRoutes with ClientSearchRoutes {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val executionContext: ExecutionContextExecutor

  final val combinedRoutes: Route = clientSearchRoutes ~ searchCountRoutes
}
