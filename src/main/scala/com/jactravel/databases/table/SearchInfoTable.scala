package com.jactravel.databases.table

import com.jactravel.databases.entity.SearchInfoRecord
import com.outworkers.phantom.Row
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{Ascending, ClusteringOrder, ConsistencyLevel, PartitionKey, Table, context}

import scala.concurrent.Future

/**
  * Created by fayaz on 10.06.17.
  */
abstract class SearchInfoTable extends Table[SearchInfoTable, SearchInfoRecord] with RootConnector {
  object date extends StringColumn with PartitionKey
  object time extends IntColumn with ClusteringOrder with Ascending
  object searchCount extends IntColumn
  object successCount extends IntColumn
  object propertyZeroCount extends IntColumn

  override def tableName: String = "search_info"

  override def fromRow(r: Row): SearchInfoRecord = {
    SearchInfoRecord(date(r), time(r), searchCount(r), successCount(r), propertyZeroCount(r))
  }

  def getPropertyZeroCount(date: String, from: Int, to: Int): Future[List[(Int, Int)]] = {
    select(_.time, _.propertyZeroCount)
      .where(_.date eqs date)
      .and(_.time lte to)
      .and(_.time gte from)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def getSuccessCount(date: String, from: Int, to: Int): Future[List[(Int, Int)]] = {
    select(_.time, _.successCount)
      .where(_.date eqs date)
      .and(_.time lte to)
      .and(_.time gte from)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def getSearchCount(date: String, from: Int, to: Int): Future[List[(Int, Int)]] = {
    select(_.time, _.searchCount)
      .where(_.date eqs date)
      .and(_.time lte to)
      .and(_.time gte from)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def getAllEntity(date: String, limitSize: Int): Future[List[SearchInfoRecord]] = {
    select
      .where(_.date eqs date)
      .limit(limitSize)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }
}
