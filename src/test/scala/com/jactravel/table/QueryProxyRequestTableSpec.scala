package com.jactravel.table

import com.jactravel.helper.CassandraTableSpec
import com.jactravel.helper.Samples._
import com.outworkers.phantom.dsl.context
import org.joda.time.DateTime

/**
  * Created by fayaz on 16.06.17.
  */
class QueryProxyRequestTableSpec extends CassandraTableSpec {

  override def beforeAll(): Unit = database.create()
  final val table = database.queryProxyRequestTable

  val limit = 5


  "QueryProxyRequestTable.insert" should "insert entity" in {
    for {
      insertRes1 <- table.insert()
        .value(_.query_uuid, querySample.query_uuid)
        .value(_.request_utc_timestamp, querySample.request_utc_timestamp)
        .value(_.client_ip, querySample.client_ip)
        .value(_.search_query_type, querySample.search_query_type)
        .value(_.host, querySample.host)
        .value(_.client_request_utc_timestamp, querySample.request_utc_timestamp)
        .value(_.client_response_utc_timestamp, querySample.client_response_utc_timestamp)
        .value(_.forwarded_request_utc_timestamp, querySample.forwarded_request_utc_timestamp)
        .value(_.forwarded_response_utc_timestamp, querySample.forwarded_response_utc_timestamp)
        .value(_.request_xml, querySample.request_xml)
        .value(_.response_xml, querySample.response_xml)
        .value(_.xml_booking_login, querySample.xml_booking_login)
        .value(_.success, querySample.success)
        .value(_.error_message, querySample.error_message)
        .future()
      insertRes2 <- table.insert()
        .value(_.query_uuid, querySample.query_uuid + "1")
        .value(_.request_utc_timestamp, querySample.request_utc_timestamp.plus(3600))
        .value(_.client_ip, querySample.client_ip)
        .value(_.search_query_type, querySample.search_query_type)
        .value(_.host, querySample.host)
        .value(_.client_request_utc_timestamp, querySample.request_utc_timestamp.plus(3600))
        .value(_.client_response_utc_timestamp, querySample.client_response_utc_timestamp.plus(3600))
        .value(_.forwarded_request_utc_timestamp, querySample.forwarded_request_utc_timestamp.plus(3600))
        .value(_.forwarded_response_utc_timestamp, querySample.forwarded_response_utc_timestamp.plus(3600))
        .value(_.request_xml, querySample.request_xml)
        .value(_.response_xml, querySample.response_xml)
        .value(_.xml_booking_login, querySample.xml_booking_login)
        .value(_.success, "1")
        .value(_.error_message, querySample.error_message)
        .future()
    } yield {
      checks(insertRes1)
      checks(insertRes2)
    }
  }

  "QueryProxyRequestTable.getSearchesCountByTime" should "return list of available entity" in {
    val from = DateTime.now.minus(36000 * 1000)
    val to = DateTime.now.plus(36000 * 1000)

    table.getSearchesCountByTime(from, to, limit) map { lst =>
      lst.size shouldEqual 2
    }
  }

  "QueryProxyRequestTable.getSearchesCountByTime" should "return Nil of entity" in {
    val from = DateTime.now.minus(36000 * 1000)
    val to = DateTime.now.minus(3600 * 1000)

    table.getSearchesCountByTime(from, to, limit) map { lst =>
      lst shouldEqual Nil
    }
  }

  "QueryProxyRequestTable.getSearchesSuccessCountByTime" should "return list of available success entity" in {
    val from = DateTime.now.minus(36000 * 1000)
    val to = DateTime.now.plus(36000 * 1000)

    table.getSearchesSuccessCountByTime(from, to) map { lst =>
      lst.size shouldEqual 1
    }
  }

  "QueryProxyRequestTable.getSearchesSuccessCountByTime" should "return Nil of available success entity" in {
    val from = DateTime.now.minus(36000 * 1000)
    val to = DateTime.now.minus(3600 * 1000)

    table.getSearchesSuccessCountByTime(from, to) map { lst =>
      lst.size shouldEqual Nil
    }
  }

}
