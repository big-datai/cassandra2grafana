package com.jactravel

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.jactravel.routes.Routes
import com.jactravel.utils.Configuration._
import com.jactravel.utils.DefaultLogging.log

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 05.06.17.
  */
object WebServer extends App with Routes {
  override implicit val actorSystem = ActorSystem()
  override implicit val materializer = ActorMaterializer()
  override implicit val executionContext: ExecutionContextExecutor = actorSystem.dispatcher

  log.info(s"Starting server on $serverHost:$serverPort...")
  Http().bindAndHandle(combinedRoutes, serverHost, serverPort)
}
