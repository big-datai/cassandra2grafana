package com.jactravel.databases.table

import com.jactravel.databases.entity.QueryProxyRequestRecord
import com.outworkers.phantom.Row
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{Ascending, ClusteringOrder, ConsistencyLevel, PartitionKey, Table, context}
import com.outworkers.phantom.streams._
import org.joda.time.DateTime
import play.api.libs.iteratee.{Enumeratee, Iteratee}

import scala.concurrent.Future

/**
  * Created by fayaz on 14.06.17.
  */
abstract class QueryProxyRequestTable extends Table[QueryProxyRequestTable, QueryProxyRequestRecord] with RootConnector {
  object query_uuid extends StringColumn with PartitionKey
  object request_utc_timestamp extends DateTimeColumn with PartitionKey
  object client_ip extends StringColumn
  object search_query_type extends IntColumn
  object host extends StringColumn
  object client_request_utc_timestamp extends DateTimeColumn with ClusteringOrder with Ascending
  object client_response_utc_timestamp extends DateTimeColumn with ClusteringOrder with Ascending
  object forwarded_request_utc_timestamp extends DateTimeColumn
  object forwarded_response_utc_timestamp extends DateTimeColumn
  object request_xml extends StringColumn
  object response_xml extends StringColumn
  object xml_booking_login extends StringColumn
  object success extends StringColumn
  object error_message extends StringColumn

  override def tableName: String = "query_proxy_request"

  override def fromRow(r: Row): QueryProxyRequestRecord = {
    QueryProxyRequestRecord(
      query_uuid(r),
      request_utc_timestamp(r),
      client_ip(r),
      search_query_type(r),
      host(r),
      client_request_utc_timestamp(r),
      client_response_utc_timestamp(r),
      forwarded_request_utc_timestamp(r),
      forwarded_response_utc_timestamp(r),
      request_xml(r),
      response_xml(r),
      xml_booking_login(r),
      success(r),
      error_message(r)
    )
  }

  def getSearchesCountByTime(from: DateTime, to: DateTime): Future[List[DateTime]] = {
    select(_.client_request_utc_timestamp)
      .where(_.client_request_utc_timestamp lte to)
      .and(_.client_request_utc_timestamp gte from)
      .allowFiltering()
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  def getSearchesSuccessCountByTime(from: DateTime, to: DateTime): Future[List[DateTime]] = {
    select(_.client_request_utc_timestamp)
      .where(_.client_request_utc_timestamp lte to)
      .and(_.client_request_utc_timestamp gte from)
      .and(_.success is "0")
      .allowFiltering()
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }

  // Experimental
  def getSearchesCountByTimeStreamly(from: DateTime, to: DateTime): Future[List[DateTime]] = {

    val enumFilter: Enumeratee[QueryProxyRequestRecord, QueryProxyRequestRecord] = {
      Enumeratee.filter[QueryProxyRequestRecord](e =>
        (e.client_request_utc_timestamp isBefore to) &&
        (e.client_request_utc_timestamp isAfter from))
    }

    val enumMapper: Enumeratee[QueryProxyRequestRecord, DateTime] = {
      Enumeratee.mapInput[QueryProxyRequestRecord](e => e.map(_.client_request_utc_timestamp))
    }

    select.fetchEnumerator()
      .through(enumFilter)
      .through(enumMapper)
      .run(Iteratee.getChunks)
  }
}
