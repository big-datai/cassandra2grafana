package com.jactravel.utils

import com.datastax.driver.core.SocketOptions
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoint, ContactPoints}
import com.typesafe.config.ConfigFactory

/**
  * Created by fayaz on 27.05.17.
  */
object Connection {

  private val cs = "cassandra"
  private val cassandraConfig = ConfigFactory.load()

  val cassandraUsername: String = cassandraConfig.getString(s"$cs.username")
  val cassandraPassword: String = cassandraConfig.getString(s"$cs.password")
  val cassandraHosts: List[String] = cassandraConfig.getString(s"$cs.hosts").split(",").toList
  val cassandraPort: Int = cassandraConfig.getInt(s"$cs.port")
  val cassandraKeySpace: String = cassandraConfig.getString(s"$cs.keyspace")

  lazy val connector: CassandraConnection = ContactPoints(cassandraHosts, cassandraPort)
    .withClusterBuilder(_.withSocketOptions(
      new SocketOptions()
        .setReadTimeoutMillis(1500)
        .setConnectTimeoutMillis(20000)
    ))
    .keySpace(cassandraKeySpace)

  /**
    * Create an embedded connector, testing purposes only
    */
  lazy val testConnector: CassandraConnection = ContactPoint.embedded.noHeartbeat().keySpace("testKeySpace")

}
