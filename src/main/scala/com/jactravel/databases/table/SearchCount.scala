package com.jactravel.databases.table

import com.jactravel.databases.entity.SearchCountRecord
import com.jactravel.utils.DefaultLogging
import com.outworkers.phantom.Row
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{ConsistencyLevel, PartitionKey, Table, context}

import scala.concurrent.Future

/**
  * Created by fayaz on 10.06.17.
  */
abstract class SearchCount extends Table[SearchCount, SearchCountRecord] with RootConnector {
  object minute extends StringColumn with PartitionKey
  object date extends StringColumn
  object count extends LongColumn

  override def fromRow(r: Row): SearchCountRecord = {
    SearchCountRecord(minute(r), date(r), count(r))
  }

  def get(from: String, to: String): Future[List[SearchCountRecord]] = {
    select
      .where(_.date isLte to)
      .and(_.date isGte from)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }
}
