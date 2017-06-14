package com.jactravel.databases.entity

import org.joda.time.DateTime

/**
  * Created by fayaz on 14.06.17.
  */
case class BookingRequestRecord(trade_id: Int,
                                query_uuid: String,
                                search_query_uuid: String,
                                pre_book_query_uuid: String,
                                search_processor: Int,
                                host: String,
                                start_utc_timestamp: DateTime,
                                end_utc_timestamp: DateTime,
                                brand_id: Int,
                                sales_channel_id: Int,
                                property_id: Int,
                                arrival_date: String,
                                duration: Int,
                                rooms: List[BookRoomInfo],
                                currency_id: Int,
                                pre_booking_token: String)

case class BookRoomInfo(adults: Int,
                        children: Int,
                        child_ages: Int,
                        meal_basic_id: Int,
                        booking_token: String,
                        property_room_type_id: Int,
                        price_diff: Int)
