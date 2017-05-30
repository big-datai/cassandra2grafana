package utils

import com.outworkers.phantom.connectors.{CassandraConnection, ContactPoint, ContactPoints}
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._

/**
  * Created by fayaz on 27.05.17.
  */
object Connection {

  private val cs = "cassandra"
  private val cassandraConfig = ConfigFactory.load()

  val cassandraUsername: String = cassandraConfig.getString(s"$cs.username")
  val cassandraPassword: String = cassandraConfig.getString(s"$cs.password")
  val cassandraHosts: Seq[String] = cassandraConfig.getStringList(s"$cs.hosts").toSeq
  val cassandraPort: Int = cassandraConfig.getInt(s"$cs.port")
  val cassandraKeySpace: String = cassandraConfig.getString(s"$cs.keyspace")

  lazy val connector: CassandraConnection = ContactPoints(cassandraHosts, cassandraPort).keySpace(cassandraKeySpace)

  /**
    * Create an embedded connector, testing purposes only
    */
  lazy val testConnector: CassandraConnection = ContactPoint.embedded.noHeartbeat().keySpace("testKeySpace")

}
