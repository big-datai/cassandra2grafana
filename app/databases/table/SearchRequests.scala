package databases.table

import java.util.UUID

import com.outworkers.phantom.ResultSet
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl._
import databases.model.SearchRequest._
import databases.model._
import play.api.libs.json.Json

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

  object roomRequests extends JsonListColumn[RoomRequest] {
    override def fromJson(obj: String): RoomRequest = {
      Json.parse(obj).as[RoomRequest]
    }

    override def toJson(obj: RoomRequest): String = {
      Json.stringify(Json.toJson(obj))
    }
  }

  override def fromRow(r: Row): SearchRequest =
    SearchRequest(id(r),
      login(r),
      password(r),
      currencyId(r),
      arrival(r),
      duration(r),
      regionId(r),
      mealBasisId(r),
      minStarRating(r),
      roomRequests(r))

  def find(id: UUID): Future[Option[SearchRequest]] = {
    select
      .where(_.id eqs id)
      .one()
  }

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
      .value(_.roomRequests, sr.roomRequests)
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
      .and(_.roomRequests setTo sr.roomRequests)
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .future
  }

  def remove(id: UUID): Future[ResultSet] = {
    delete
      .where(_.id eqs id)
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .future()
  }

  def findAll: Future[List[SearchRequest]] = {
    select.fetch()
  }
}
