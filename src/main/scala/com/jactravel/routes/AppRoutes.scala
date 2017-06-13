package com.jactravel.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import com.jactravel.routes.swagger.SwaggerDocRoutes

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 07.06.17.
  */
trait AppRoutes extends ApplicationRoutes with SearchInfoRoutes with SwaggerDocRoutes {
  implicit val actorSystem: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val executionContext: ExecutionContextExecutor

  final val combinedRoutes: Route = cors() {
    routes ~ applicationRoutes ~ searchInfoRoutes
  }
}
