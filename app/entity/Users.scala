package entity

import com.outworkers.phantom.ResultSet
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.dsl._
import org.joda.time.DateTime

import scala.concurrent.Future

/**
  * Created by fayaz on 27.05.17.
  */
case class User(id: UUID, firstName: String, lastName: String, created: DateTime)

abstract class Users extends CassandraTable[Users, User] with RootConnector {
  object id extends UUIDColumn(this) with PartitionKey
  object firstName extends StringColumn(this)
  object lastName extends StringColumn(this)
  object created extends DateTimeColumn(this)

  override def fromRow(r: Row): User = User(id(r), firstName(r), lastName(r), created(r))

  def find(id: UUID): Future[Option[User]] = {
    select.where(_.id eqs id).one()
  }

  def save(u: User): Future[ResultSet] = {
    insert()
      .value(_.id, u.id)
      .value(_.firstName, u.firstName)
      .value(_.lastName, u.lastName)
      .value(_.created, u.created)
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
