package com.jactravel.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpEntity, HttpResponse}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 07.06.17.
  */
trait CustomRoutes {
  implicit val system: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val executionContext: ExecutionContextExecutor

  val clientSearchRoutes: Route = {
    pathSingleSlash {
      get {
        complete(HttpResponse(entity = HttpEntity("test connection")))
      }
    }
  }
}
