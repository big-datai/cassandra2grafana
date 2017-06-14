package com.jactravel.databases.entity

import org.joda.time.DateTime

/**
  * Created by fayaz on 14.06.17.
  */
case class QueryProxyRequestRecord(query_uuid: String,
                                   request_utc_timestamp: DateTime,
                                   client_ip: String,
                                   search_query_type: Int,
                                   host: String,
                                   client_request_utc_timestamp: DateTime,
                                   client_response_utc_timestamp: DateTime,
                                   forwarded_request_utc_timestamp: DateTime,
                                   forwarded_response_utc_timestamp: DateTime,
                                   request_xml: String,
                                   response_xml: String,
                                   xml_booking_login: String,
                                   success: String,
                                   error_message: String)
