package com.jactravel.helper

import com.jactravel.databases.entity.QueryProxyRequestRecord
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
}
