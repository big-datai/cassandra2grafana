package databases

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.Database
import entity.Users
import utils.CassandraConnector

/**
  * Created by fayaz on 27.05.17.
  */
class CassandraDatabase(val keyspace: CassandraConnection) extends Database[CassandraDatabase](keyspace) {
  object users extends Users with Connector
}

object Databases {
  private object CassandraDatabase extends CassandraDatabase(CassandraConnector.connector)

  val userTable = CassandraDatabase.users
}
