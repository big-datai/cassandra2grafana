package databases

import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl.KeySpaceDef
import databases.table.SearchRequests
import utils.Connection

/**
  * Created by fayaz on 27.05.17.
  */
class CassandraDatabase(val keySpace: KeySpaceDef) extends Database[CassandraDatabase](keySpace) {
  object searchRequests extends SearchRequests with Connector
}

object Tables {
  object CassandraDatabase extends CassandraDatabase(Connection.connector)

  /**
    * CREATE TABLE ${keyspace}.searchrequests (id uuid PRIMARY KEY , loginDetails text, searchDetails text, roomRequests list<text>);
    */
  final val searchRequestsTable = CassandraDatabase.searchRequests
}
