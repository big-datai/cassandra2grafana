package com.jactravel.utils

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}

import scala.concurrent.Future

/**
  * Created by fayaz on 22.06.17.
  */
object Types {

  // Alias types
  type TimeSeries = (Int, Long)

  // Function result type
  type AsyncCall = PartialFunction[HttpRequest, Future[HttpResponse]]
}
