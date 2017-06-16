package com.jactravel.helper

import com.jactravel.databases.entity.{BookingRequestRecord, QueryProxyRequestRecord}
import org.joda.time.DateTime

/**
  * Created by fayaz on 16.06.17.
  */
object Samples {

  final val querySample = QueryProxyRequestRecord(
    "query_uuid",
    DateTime.now(),
    "client_ip",
    0,
    "host",
    DateTime.now(),
    DateTime.now(),
    DateTime.now(),
    DateTime.now(),
    "request_xml",
    "response_xml",
    "xml_booking_login",
    "0",
    "error_message"
  )

  final val bookingSample = BookingRequestRecord(
    1,
    "query_uuid",
    "search_query_uuid",
    "pre_book_query_uuid",
    1,
    "host",
    DateTime.now(),
    DateTime.now(),
    1,
    1,
    1,
    "arrival_date",
    1,
    Nil,
    1,
    "pre_booking_token"
  )
}
