package com.jactravel.databases.entity

/**
  * Created by fayaz on 04.06.17.
  */
case class ClientSearchRecord(SearchQueryUUID: String,
                              ClientIP: String = "",
                              Host: String = "",
                              ClientRequestTimestamp: String = "",
                              ClientResponseTimestamp: String = "",
                              ForwardedRequestTimestamp: String = "",
                              ForwardedResponseTimestamp: String = "",
                              TradeID: Int = 0,
                              BrandID: Int = 0,
                              SalesChannelID: Int = 0,
                              GeographyLevel1ID: Int = 0,
                              GeographyLevel2ID: Int = 0,
                              GeographyLevel3ID: List[Int] = Nil,
                              PropertyID: List[Int] = Nil,
                              PropertyReferenceID: List[Int] = Nil,
                              ArrivalDate: String = "",
                              Duration: Int = 0,
                              Rooms: Int = 0,
                              Adults: List[Int] = Nil,
                              Children: List[Int] = Nil,
                              ChildAges: List[Int] = Nil,
                              MealBasisID: Int = 0,
                              MinStarRating: String = "",
                              HotelCount: Int = 0,
                              Success: String = "",
                              ErrorMessage: String = "",
                              SuppliersSearched: Int = 0,
                              RequestXML: String = "",
                              ResponseXML: String = "")