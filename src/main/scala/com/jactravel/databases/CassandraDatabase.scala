package com.jactravel.databases

import com.jactravel.databases.table.{ClientSearch, SearchCount}
import com.jactravel.utils.Connection
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl.KeySpaceDef

/**
  * Created by fayaz on 27.05.17.
  */
class CassandraDatabase(val keySpace: KeySpaceDef) extends Database[CassandraDatabase](keySpace) {
  object clientSearch extends ClientSearch with Connector
  object searchCount extends SearchCount with Connector
}

object Tables {
  object CassandraDatabase extends CassandraDatabase(Connection.connector)

  final val clientSearchTable = CassandraDatabase.clientSearch
  final val searchCountTable = CassandraDatabase.searchCount
}
