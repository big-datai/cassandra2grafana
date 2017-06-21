package com.jactravel.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.jactravel.routes.api.{ApplicationRoutes, GrafanaRoutes}

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 07.06.17.
  */
trait Routes
  extends ApplicationRoutes with GrafanaRoutes {
  implicit val actorSystem: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val executionContext: ExecutionContextExecutor

  final val combinedRoutes: Route = applicationRoutes ~ grafanaRoutes
}
