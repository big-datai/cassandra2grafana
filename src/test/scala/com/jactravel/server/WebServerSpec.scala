package com.jactravel.server

import akka.actor.ActorSystem
import akka.http.scaladsl.model.HttpMethods.GET
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshal
import com.jactravel.routes.Routes
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.ExecutionContextExecutor


/**
  * Created by fayaz on 07.06.17.
  */
class WebServerSpec
  extends WordSpec
    with Matchers
    with Routes
    with ScalatestRouteTest {

  implicit val actorSystem: ActorSystem = system
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  "WebServer" should {
    "return OK on GET / response" in {
      combinedRoutes(HttpRequest(GET, Uri("/"))) map { response =>
        response.status shouldEqual StatusCodes.OK
        Unmarshal(response.entity.withContentType(ContentTypes.`text/plain(UTF-8)`)).to[String] shouldEqual "Test connection"
      }
    }
  }
}
