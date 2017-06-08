package com.jactravel.databases.table

import com.jactravel.databases.entity.ClientSearchEntity
import com.outworkers.phantom.Row
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{ConsistencyLevel, PartitionKey, Table, context}

import scala.concurrent.Future

/**
  * Created by fayaz on 04.06.17.
  */
abstract class ClientSearch extends Table[ClientSearch, ClientSearchEntity] with RootConnector {
  object SearchQueryUUID extends StringColumn with PartitionKey
  object ClientIP extends StringColumn
  object Host extends StringColumn
  object ClientRequestTimestamp extends StringColumn
  object ClientResponseTimestamp extends StringColumn
  object ForwardedRequestTimestamp extends StringColumn
  object ForwardedResponseTimestamp extends StringColumn
  object TradeID extends IntColumn
  object BrandID extends IntColumn
  object SalesChannelID extends IntColumn
  object GeographyLevel1ID extends IntColumn
  object GeographyLevel2ID extends IntColumn
  object GeographyLevel3ID extends ListColumn[Int]
  object PropertyID extends ListColumn[Int]
  object PropertyReferenceID extends ListColumn[Int]
  object ArrivalDate extends StringColumn
  object Duration extends IntColumn
  object Rooms extends IntColumn
  object Adults extends ListColumn[Int]
  object Children extends ListColumn[Int]
  object ChildAges extends ListColumn[Int]
  object MealBasisID extends IntColumn
  object MinStarRating extends StringColumn
  object HotelCount extends IntColumn
  object Success extends StringColumn
  object ErrorMessage extends StringColumn
  object SuppliersSearched extends IntColumn
  object RequestXML extends StringColumn
  object ResponseXML extends StringColumn

  override def fromRow(r: Row): ClientSearchEntity = {
    ClientSearchEntity(
      SearchQueryUUID(r),
      ClientIP(r),
      Host(r),
      ClientRequestTimestamp(r),
      ClientResponseTimestamp(r),
      ForwardedRequestTimestamp(r),
      ForwardedResponseTimestamp(r),
      TradeID(r),
      BrandID(r),
      SalesChannelID(r),
      GeographyLevel1ID(r),
      GeographyLevel2ID(r),
      GeographyLevel3ID(r),
      PropertyID(r),
      PropertyReferenceID(r),
      ArrivalDate(r),
      Duration(r),
      Rooms(r),
      Adults(r),
      Children(r),
      ChildAges(r),
      MealBasisID(r),
      MinStarRating(r),
      HotelCount(r),
      Success(r),
      ErrorMessage(r),
      SuppliersSearched(r),
      RequestXML(r),
      ResponseXML(r)
    )
  }

  def get(queryUUID: String): Future[Option[ClientSearchEntity]] = {
    select
      .where(_.SearchQueryUUID eqs queryUUID)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .one()
  }

  def get(from: String, to: String): Future[List[ClientSearchEntity]] = {
    select
      .where(_.ClientRequestTimestamp isLte to)
      .and(_.ClientRequestTimestamp isGte from)
      .consistencyLevel_=(ConsistencyLevel.ONE)
      .fetch()
  }
}
