package databases

import com.outworkers.phantom.connectors.CassandraConnection
import com.outworkers.phantom.database.Database
import table.SearchRequests
import utils.CassandraConnector

/**
  * Created by fayaz on 27.05.17.
  */
class CassandraDatabase(val keyspace: CassandraConnection) extends Database[CassandraDatabase](keyspace) {
  object requests extends SearchRequests with Connector
}

object Databases {
  object CassandraDatabase extends CassandraDatabase(CassandraConnector.connector)

  /**
    * CREATE TABLE test.searchrequests (id uuid PRIMARY KEY , loginDetails text, searchDetails text, roomRequests list<text>);
    */
  val requestsTable = CassandraDatabase.requests
}
