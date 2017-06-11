package com.jactravel.databases.table

import com.jactravel.databases.entity.DayStatisticsRecord
import com.outworkers.phantom.Row
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{Ascending, ClusteringOrder, ConsistencyLevel, PartitionKey, Table, context}

import scala.concurrent.Future

/**
  * Created by fayaz on 11.06.17.
  */
abstract class DayStatisticsTable extends Table[DayStatisticsTable, DayStatisticsRecord] with RootConnector {
  object date extends StringColumn with PartitionKey
  object day extends IntColumn with ClusteringOrder with Ascending
  object count extends LongColumn

  override def tableName: String = "day_statistics"

  override def fromRow(r: Row): DayStatisticsRecord = {
    DayStatisticsRecord(date(r), day(r), count(r))
  }

  def get(date: String, from: Int, to: Int): Future[List[(Int, Long)]] = {
    select(_.day, _.count)
      .where(_.date eqs date)
      .and(_.day lte to)
      .and(_.day gte from)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def getAll(date: String, limitSize: Int): Future[List[DayStatisticsRecord]] = {
    select
      .where(_.date eqs date)
      .limit(limitSize)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }
}
