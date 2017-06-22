package com.jactravel.routes.api

import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import com.jactravel.utils.Types.AsyncCall

import scala.concurrent.Future

/**
  * Created by fayaz on 13.06.17.
  */
trait ApplicationRoutes {

  /**
    * Application routes
    */
  implicit val materializer: ActorMaterializer

  final val asyncApplicationRoutes: AsyncCall = {
    case HttpRequest(GET, Uri.Path("/"), _, _, _) =>
      Future.successful(HttpResponse(entity = "Test connection"))
  }

  final val discarding: AsyncCall = {
    case r: HttpRequest =>
      r.discardEntityBytes() // important to drain incoming HTTP Entity stream
      Future.successful(HttpResponse(404, entity = "Unknown resource!"))
  }
}
