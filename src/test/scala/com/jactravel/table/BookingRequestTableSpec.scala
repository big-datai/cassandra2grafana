package com.jactravel.table

import com.jactravel.helper.CassandraTableSpec
import com.jactravel.helper.Samples._
import com.outworkers.phantom.dsl.context
import org.joda.time.DateTime


/**
  * Created by fayaz on 16.06.17.
  */
class BookingRequestTableSpec extends CassandraTableSpec {

  override def beforeAll(): Unit = database.create()
  final val table = database.bookingRequestTable

  val limit = 5
  "BookingRequestTable.getBookingCountByTime" should "correctly insert entity" in {
    for {
      insertRes <- table.insert
        .value(_.trade_id, bookingSample.trade_id)
        .value(_.query_uuid, bookingSample.query_uuid)
        .value(_.search_query_uuid, bookingSample.search_query_uuid)
        .value(_.pre_book_query_uuid, bookingSample.pre_book_query_uuid)
        .value(_.search_processor, bookingSample.search_processor)
        .value(_.host, bookingSample.host)
        .value(_.start_utc_timestamp, bookingSample.start_utc_timestamp)
        .value(_.end_utc_timestamp, bookingSample.end_utc_timestamp)
        .value(_.brand_id, bookingSample.brand_id)
        .value(_.sales_channel_id, bookingSample.sales_channel_id)
        .value(_.property_id, bookingSample.property_id)
        .value(_.arrival_date, bookingSample.arrival_date)
        .value(_.duration, bookingSample.duration)
        .value(_.rooms, bookingSample.rooms)
        .value(_.currency_id, bookingSample.currency_id)
        .value(_.pre_booking_token, bookingSample.pre_booking_token)
        .future()
    } yield checks(insertRes)
  }
  "BookingRequestTable.getBookingCountByTime" should "return non empty list of booking entity" in {
    val from = DateTime.now.minus(36000 * 1000)
    val to = DateTime.now.plus(36000 * 1000)

    table.getBookingCountByTime(from, to, limit) map { lst =>
      lst.size shouldEqual 1
    }
  }

  "BookingRequestTable.getBookingCountByTime" should "return Nil" in {
    val from = DateTime.now.minus(36000 * 1000)
    val to = DateTime.now.minus(3600 * 1000)

    table.getBookingCountByTime(from, to, limit) map { lst =>
      lst shouldEqual Nil
    }
  }
}
