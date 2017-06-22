package com.jactravel.routes

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer
import com.jactravel.routes.api.{ApplicationRoutes, GrafanaRoutes}

import scala.concurrent.{ExecutionContextExecutor, Future}

/**
  * Created by fayaz on 07.06.17.
  */
trait Routes
  extends ApplicationRoutes with GrafanaRoutes {
  implicit val actorSystem: ActorSystem
  implicit val materializer: ActorMaterializer
  implicit val executionContext: ExecutionContextExecutor

  final val combinedRoutes: PartialFunction[HttpRequest, Future[HttpResponse]] = asyncApplicationRoutes orElse grafanaRoutes orElse discarding
}
