package com.jactravel

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.jactravel.routes.AppRoutes
import com.jactravel.utils.{Configuration, DefaultLogging}

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 05.06.17.
  */
object WebServer extends App with AppRoutes with Configuration with DefaultLogging {
  override implicit val system = ActorSystem()
  override implicit val materializer = ActorMaterializer()
  override implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  log.info(s"Starting server on $host:$port...")
  Http().bindAndHandle(combinedRoutes, host, port)
}
