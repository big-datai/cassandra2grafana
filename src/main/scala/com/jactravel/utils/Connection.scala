package com.jactravel.utils

import com.datastax.driver.core.SocketOptions
import com.jactravel.utils.Configuration._
import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoint, ContactPoints}

/**
  * Created by fayaz on 27.05.17.
  */
object Connection {

  /**
    * Cassandra connector
    */
  lazy val connector: CassandraConnection = ContactPoints(cassandraHosts, cassandraPort)
    .withClusterBuilder(_.withSocketOptions(
      new SocketOptions()
        .setReadTimeoutMillis(15000)
        .setConnectTimeoutMillis(20000)
    ))
    .keySpace(cassandraKeySpace)

  /**
    * Create an embedded connector, testing purposes only
    */
  lazy val testConnector: CassandraConnection = ContactPoint.embedded.noHeartbeat().keySpace("testKeySpace")

}
