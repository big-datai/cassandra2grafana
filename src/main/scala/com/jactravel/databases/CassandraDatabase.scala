package com.jactravel.databases

import com.jactravel.databases.table._
import com.jactravel.utils.Connection
import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl.KeySpaceDef

/**
  * Created by fayaz on 27.05.17.
  */
class CassandraDatabase(val keySpace: KeySpaceDef) extends Database[CassandraDatabase](keySpace) {
  object clientSearch extends ClientSearchTable with Connector
  object hoursTable extends HourStatisticsTable with Connector
  object daysTable extends DayStatisticsTable with Connector
  object weeksTable extends WeekStatisticsTable with Connector
  object yearsTable extends YearStatisticsTable with Connector
}

object Tables {
  object CassandraDatabase extends CassandraDatabase(Connection.connector)

  final val clientSearchTable = CassandraDatabase.clientSearch
  final val hoursTable = CassandraDatabase.hoursTable
  final val daysTable = CassandraDatabase.daysTable
  final val weeksTable = CassandraDatabase.weeksTable
  final val yearsTable = CassandraDatabase.yearsTable
}
