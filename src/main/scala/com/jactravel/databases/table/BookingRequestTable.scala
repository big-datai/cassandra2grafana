package com.jactravel.databases.table

import com.jactravel.databases.entity.{BookRoomInfo, BookingRequestRecord}
import com.jactravel.utils.JsonSupport
import com.outworkers.phantom.Row
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{Ascending, ClusteringOrder, PartitionKey, Table}
import spray.json._

/**
  * Created by fayaz on 14.06.17.
  */
abstract class BookingRequestTable extends Table[BookingRequestTable, BookingRequestRecord] with RootConnector with JsonSupport {
  object trade_id extends IntColumn with PartitionKey
  object query_uuid extends StringColumn with PartitionKey
  object search_query_uuid extends StringColumn with ClusteringOrder with Ascending
  object pre_book_query_uuid extends StringColumn
  object search_processor extends IntColumn
  object host extends StringColumn
  object start_utc_timestamp extends DateTimeColumn
  object end_utc_timestamp extends DateTimeColumn
  object brand_id extends IntColumn
  object sales_channel_id extends IntColumn
  object property_id extends IntColumn
  object arrival_date extends StringColumn
  object duration extends IntColumn
  object rooms extends JsonListColumn[BookRoomInfo] {
    override def fromJson(obj: String): BookRoomInfo = JsonParser(obj).convertTo[BookRoomInfo]
    override def toJson(obj: BookRoomInfo): String = obj.toJson.toString()
  }
  object currency_id extends IntColumn
  object pre_booking_token extends StringColumn

  override def tableName: String = "book_request"

  override def fromRow(r: Row): BookingRequestRecord = {
    BookingRequestRecord(
      trade_id(r),
      query_uuid(r),
      search_query_uuid(r),
      pre_book_query_uuid(r),
      search_processor(r),
      host(r),
      start_utc_timestamp(r),
      end_utc_timestamp(r),
      brand_id(r),
      sales_channel_id(r),
      property_id(r),
      arrival_date(r),
      duration(r),
      rooms(r),
      currency_id(r),
      pre_booking_token(r)
    )
  }
}
