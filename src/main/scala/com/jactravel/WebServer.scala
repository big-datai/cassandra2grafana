package com.jactravel

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.jactravel.routes.CustomRoutes
import com.jactravel.utils.{Configuration, JsonSupport}

import scala.concurrent.ExecutionContextExecutor

/**
  * Created by fayaz on 05.06.17.
  */
object WebServer extends App with JsonSupport with CustomRoutes with Configuration {

  override implicit val system = ActorSystem()
  override implicit val materializer = ActorMaterializer()
  override implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  Http().bindAndHandle(clientSearchRoutes, host, port)
}
