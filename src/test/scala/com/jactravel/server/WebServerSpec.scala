package com.jactravel.server

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.jactravel.routes.AppRoutes
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.ExecutionContextExecutor


/**
  * Created by fayaz on 07.06.17.
  */
class WebServerSpec
  extends WordSpec
    with Matchers
    with ScalatestRouteTest
    with AppRoutes {

  override implicit val executionContext: ExecutionContextExecutor = system.dispatcher


  "WebServer" should {
    "return OK response" in {
      Get("/") ~> combinedRoutes ~> check {
        status shouldEqual StatusCodes.OK
        responseAs[String] shouldEqual "test connection"
      }
    }
  }
}
