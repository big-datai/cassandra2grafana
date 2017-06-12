package com.jactravel.utils.swagger

import akka.http.scaladsl.model.{HttpEntity, MediaTypes}
import akka.http.scaladsl.server.Route
import com.github.swagger.akka._
import com.github.swagger.akka.model.Info
import com.jactravel.routes.SearchInfoRoutes
import com.jactravel.utils.Configuration.swaggerPort
import io.swagger.models.ExternalDocs

import scala.reflect.runtime.{universe => ru}

/**
  * Created by fayaz on 12.06.17.
  */
trait SwaggerDocService extends SwaggerHttpService with HasActorSystem {

  override val apiTypes = Seq(ru.typeOf[SearchInfoRoutes])

  override val host = s"localhost:$swaggerPort"

  override val basePath = "/"

  override val info = Info(
    version = "1.0",
    title = "JacTravel Monitoring API",
    description = "Monitoring system based on Apache Cassandra"
  )

  override val apiDocsPath: String = ""

  override val externalDocs = Some(new ExternalDocs("Core Docs", "https://www.wikipedia.org/"))

  override lazy val routes: Route = {
    path("docs") {
      get {
        complete(HttpEntity(MediaTypes.`application/json`, generateSwaggerJson))
      }
    }
  }
}
