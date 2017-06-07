package com.jactravel.helper

import com.jactravel.databases.CassandraDatabase
import com.jactravel.utils.Connection.testConnector
/**
  * Created by fayaz on 29.05.17.
  */

object EmbeddedCassandra {
  object EmbeddedDb extends CassandraDatabase(testConnector)

  trait EmbeddedCassandraLike {
    final val database = EmbeddedDb
  }
}
