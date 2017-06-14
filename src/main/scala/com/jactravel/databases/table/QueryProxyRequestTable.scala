package com.jactravel.databases.table

import com.jactravel.databases.entity.QueryProxyRequestRecord
import com.outworkers.phantom.Row
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{Ascending, ClusteringOrder, ConsistencyLevel, PartitionKey, Table, context}
import org.joda.time.DateTime

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

  def getRecordByTime(from: DateTime, to: DateTime): Future[List[QueryProxyRequestRecord]] = {
    select
      .where(_.client_request_utc_timestamp lte to)
      .and(_.client_request_utc_timestamp gte from)
      .allowFiltering()
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }
}
