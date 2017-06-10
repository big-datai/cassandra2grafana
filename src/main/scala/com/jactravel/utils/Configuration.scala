package com.jactravel.utils

import com.typesafe.config.ConfigFactory

/**
  * Created by fayaz on 08.06.17.
  */
trait Configuration {

  private val config = ConfigFactory.load()

  /**
    * Base app configuration
    */
  final val host: String = config.getString("app.host")
  final val port: Int = config.getInt("app.port")
  final val cassandraUsername: String = config.getString("cassandra.username")
  final val cassandraPassword: String = config.getString("cassandra.password")
  final val cassandraHosts: List[String] = config.getString("cassandra.hosts").split(",").toList
  final val cassandraPort: Int = config.getInt("cassandra.port")
  final val cassandraKeySpace: String = config.getString("cassandra.keyspace")
}
