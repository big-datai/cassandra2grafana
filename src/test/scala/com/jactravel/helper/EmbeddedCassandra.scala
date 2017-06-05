package com.jactravel.helper

import databases.CassandraDatabase
import utils.Connection.testConnector
/**
  * Created by fayaz on 29.05.17.
  */

object EmbeddedCassandra {
  object EmbeddedDb extends CassandraDatabase(testConnector)

  trait EmbeddedCassandraLike {
    final val database = EmbeddedDb
  }
}
