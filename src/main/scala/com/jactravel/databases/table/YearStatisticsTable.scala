package com.jactravel.databases.table

import com.jactravel.databases.entity.YearStatisticsRecord
import com.outworkers.phantom.Row
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{ConsistencyLevel, PartitionKey, Table, context}

import scala.concurrent.Future

/**
  * Created by fayaz on 11.06.17.
  */
abstract class YearStatisticsTable extends Table[YearStatisticsTable, YearStatisticsRecord] with RootConnector {
  object year extends IntColumn with PartitionKey
  object count extends LongColumn

  override def tableName: String = "year_statistics"

  override def fromRow(r: Row): YearStatisticsRecord = {
    YearStatisticsRecord(year(r), count(r))
  }

  def get(year: Int): Future[Option[Long]] = {
    select(_.count)
      .where(_.year eqs year)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def getAll(limitSize: Int): Future[List[YearStatisticsRecord]] = {
    select
      .all()
      .limit(limitSize)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

}
