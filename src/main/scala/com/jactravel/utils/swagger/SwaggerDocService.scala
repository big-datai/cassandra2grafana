package com.jactravel.utils.swagger

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.github.swagger.akka._
import com.github.swagger.akka.model.Info
import com.jactravel.routes.HourStatisticsRoutes

import scala.reflect.runtime.{universe => ru}

/**
  * Created by fayaz on 12.06.17.
  */
class SwaggerDocService extends SwaggerHttpService with HasActorSystem {
  override implicit val actorSystem: ActorSystem = ActorSystem()
  override implicit val materializer: ActorMaterializer = ActorMaterializer()
  override val apiTypes = Seq(ru.typeOf[HourStatisticsRoutes])
  override val host = "localhost:8080"
  override val basePath = "/"
  override val info = Info(version = "1.0")
//  override val externalDocs = Some(new ExternalDocs("Core Docs", "http://acme.com/docs"))
}
