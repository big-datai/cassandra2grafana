package table

import java.util.UUID

import com.outworkers.phantom.ResultSet
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl._
import model.SearchRequest._
import model._
import play.api.libs.json.Json

import scala.concurrent.Future

/**
  * Created by fayaz on 28.05.17.
  */
abstract class SearchRequests extends Table[SearchRequests, SearchRequest] with RootConnector {

  object id extends UUIDColumn with PartitionKey

  object loginDetails extends JsonColumn[LoginDetails] {
    override def fromJson(obj: String): LoginDetails = {
      Json.parse(obj).as[LoginDetails]
    }

    override def toJson(obj: LoginDetails): String = {
      Json.stringify(Json.toJson(obj))
    }
  }

  object searchDetails extends JsonColumn[SearchDetails] {
    override def fromJson(obj: String): SearchDetails = {
      Json.parse(obj).as[SearchDetails]
    }

    override def toJson(obj: SearchDetails): String = {
      Json.stringify(Json.toJson(obj))
    }
  }

  object roomRequests extends JsonListColumn[RoomRequest] {
    override def fromJson(obj: String): RoomRequest = {
      Json.parse(obj).as[RoomRequest]
    }

    override def toJson(obj: RoomRequest): String = {
      Json.stringify(Json.toJson(obj))
    }
  }

  override def fromRow(r: Row): SearchRequest = SearchRequest(id(r), loginDetails(r), searchDetails(r), roomRequests(r))

  def find(id: UUID): Future[Option[SearchRequest]] = {
    select
      .where(_.id eqs id)
      .one()
  }

  def save(sr: SearchRequest): Future[ResultSet] = {
    insert
      .value(_.id, sr.id)
      .value(_.loginDetails, sr.loginDetails)
      .value(_.searchDetails, sr.searchDetails)
      .value(_.roomRequests, sr.roomRequests)
      .consistencyLevel_=(ConsistencyLevel.ALL)
      .future()
  }

  def modify(id: UUID, sr: SearchRequestUpdate): Future[ResultSet] = {
    update
      .where(_.id eqs id)
      .modify(_.loginDetails setTo sr.loginDetails)
      .and(_.searchDetails setTo sr.searchDetails)
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
}
