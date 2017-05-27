package utils

import com.outworkers.phantom.dsl._
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._

/**
  * Created by fayaz on 27.05.17.
  */
object CassandraConnector {
  private val cassandraConfig = ConfigFactory.load()

  val cassandraHosts: Seq[String] = cassandraConfig.getStringList("cassandra.hosts").toSeq
  val cassandraPort: Int = cassandraConfig.getInt("cassandra.port")
  val cassandraKeySpace: String = cassandraConfig.getString("cassandra.keyspace")

  lazy val connector: CassandraConnection = ContactPoints(cassandraHosts, cassandraPort).keySpace(cassandraKeySpace)
}
