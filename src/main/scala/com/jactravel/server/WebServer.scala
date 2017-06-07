package com.jactravel.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.jactravel.databases.entity.ClientSearchEntity
import com.jactravel.utils.JsonSupport
import databases.Tables.clientSearchTable

/**
  * Created by fayaz on 05.06.17.
  */
object WebServer extends App with JsonSupport {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val routes: Route = {
    //todo: specify function
    path("cs" / Remaining) { str =>
      get {
        onSuccess(clientSearchTable.find(str)) {
          case Some(cs: ClientSearchEntity) => complete(cs)
          case _ => complete(StatusCodes.NoContent)
        }
      }
    }
  }

  Http().bindAndHandle(routes, "localhost", 8080)
}
