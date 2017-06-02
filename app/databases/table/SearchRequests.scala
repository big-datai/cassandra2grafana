package databases.table

import java.util.UUID

import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl.{ConsistencyLevel, DateTime, PartitionKey, Table}
import com.outworkers.phantom.{ResultSet, Row}
import databases.model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by fayaz on 28.05.17.
  */
abstract class SearchRequests extends Table[SearchRequests, SearchRequest] with RootConnector {

  object id extends UUIDColumn with PartitionKey
  object login extends StringColumn
  object password extends StringColumn
  object currencyId extends IntColumn
  object arrival extends DateTimeColumn
  object duration extends IntColumn
  object regionId extends IntColumn
  object mealBasisId extends IntColumn
  object minStarRating extends IntColumn
  object adults extends IntColumn
  object children extends IntColumn
  object childrenAges extends IntColumn

  override def fromRow(r: Row): SearchRequest = {
    SearchRequest(
      id(r),
      login(r),
      password(r),
      currencyId(r),
      arrival(r),
      duration(r),
      regionId(r),
      mealBasisId(r),
      minStarRating(r),
      adults(r),
      children(r),
      childrenAges(r)
    )
  }

  def find(id: UUID): Future[Option[SearchRequest]] = {
    select
      .where(_.id eqs id)
      .one()
  }
  //specify
  def findByDateTime(from: DateTime, to: DateTime, count: Int): Future[List[SearchRequest]] = {
    select
      .where(_.arrival isGte from)
      .and(_.arrival isLte to)
      .limit(count)
      .fetch()
  }

  def save(sr: SearchRequest): Future[ResultSet] = {
    insert
      .value(_.id, sr.id)
      .value(_.login, sr.login)
      .value(_.password, sr.password)
      .value(_.currencyId, sr.currencyId)
      .value(_.arrival, sr.arrival)
      .value(_.duration, sr.duration)
      .value(_.regionId, sr.regionId)
      .value(_.mealBasisId, sr.mealBasisId)
      .value(_.minStarRating, sr.minStarRating)
      .value(_.adults, sr.adults)
      .value(_.children, sr.children)
      .value(_.childrenAges, sr.childrenAges)
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .future()
  }

  def modify(id: UUID, sr: SearchRequestUpdate): Future[ResultSet] = {
    update
      .where(_.id eqs id)
      .modify(_.login setTo sr.login)
      .and(_.password setTo sr.password)
      .and(_.currencyId setTo sr.currencyId)
      .and(_.arrival setTo sr.arrival)
      .and(_.duration setTo sr.duration)
      .and(_.regionId setTo sr.regionId)
      .and(_.mealBasisId setTo sr.mealBasisId)
      .and(_.minStarRating setTo sr.minStarRating)
      .and(_.adults setTo sr.adults)
      .and(_.children setTo sr.children)
      .and(_.childrenAges setTo sr.childrenAges)
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .future()
  }

  def remove(id: UUID): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .future()
  }
}
