package com.jactravel.databases.table

import com.jactravel.databases.entity.WeekStatisticsRecord
import com.outworkers.phantom.Row
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{Ascending, ClusteringOrder, ConsistencyLevel, PartitionKey, Table, context}

import scala.concurrent.Future

/**
  * Created by fayaz on 11.06.17.
  */
abstract class WeekStatisticsTable extends Table[WeekStatisticsTable, WeekStatisticsRecord] with RootConnector {
  object date extends StringColumn with PartitionKey
  object week extends IntColumn with ClusteringOrder with Ascending
  object count extends LongColumn

  override def tableName: String = "week_statistics"

  override def fromRow(r: Row): WeekStatisticsRecord = {
    WeekStatisticsRecord(date(r), week(r), count(r))
  }

  def get(date: String, from: Int, to: Int): Future[List[(Int, Long)]] = {
    select(_.week, _.count)
      .where(_.date eqs date)
      .and(_.week lte to)
      .and(_.week gte from)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def getAll(date: String, limitSize: Int): Future[List[WeekStatisticsRecord]] = {
    select
      .where(_.date eqs date)
      .limit(limitSize)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }
}
