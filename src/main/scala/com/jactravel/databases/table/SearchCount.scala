package com.jactravel.databases.table

import com.jactravel.databases.entity.SearchCountRecord
import com.outworkers.phantom.Row
import com.outworkers.phantom.builder.query.SelectQuery.Default
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{Ascending, ClusteringOrder, ConsistencyLevel, PartitionKey, PrimaryKey, Table, context}

import scala.concurrent.Future

/**
  * Created by fayaz on 10.06.17.
  */
abstract class SearchCount extends Table[SearchCount, SearchCountRecord] with RootConnector {
  object date extends StringColumn with PartitionKey
  object time extends IntColumn with ClusteringOrder with Ascending
  object count extends IntColumn

  override def tableName: String = "search_count"

  override def fromRow(r: Row): SearchCountRecord = {
    SearchCountRecord(date(r), time(r), count(r))
  }

  def get(date: String, from: Int, to: Int): Future[List[SearchCountRecord]] = {
    select
      .where(_.date eqs date)
      .and(_.time lte to)
      .and(_.time gte from)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def getAll(date: String, limitSize: Int): Future[List[SearchCountRecord]] = {
    select
      .where(_.date eqs date)
      .limit(limitSize)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

}
