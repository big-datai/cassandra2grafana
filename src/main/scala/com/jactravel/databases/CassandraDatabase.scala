package com.jactravel.databases

import com.jactravel.databases.table._
import com.jactravel.utils.Connection
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl.KeySpaceDef

/**
  * Created by fayaz on 27.05.17.
  */
class CassandraDatabase(val keySpace: KeySpaceDef) extends Database[CassandraDatabase](keySpace) {
  object queryProxyRequest extends QueryProxyRequestTable with Connector
  object bookingRequestTable extends BookingRequestTable with Connector
}

object Tables {
  object CassandraDatabase extends CassandraDatabase(Connection.connector)

  final val queryProxyRequestTable = CassandraDatabase.queryProxyRequest
  final val bookingRequestTable = CassandraDatabase.bookingRequestTable
}
