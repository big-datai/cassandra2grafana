package databases

import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl.KeySpaceDef
import table.SearchRequests
import utils.Connection

/**
  * Created by fayaz on 27.05.17.
  */
class CassandraDatabase(val keySpace: KeySpaceDef) extends Database[CassandraDatabase](keySpace) {
  object requests extends SearchRequests with Connector
}

class Tables {
  object CassandraDatabase extends CassandraDatabase(Connection.connector)

  /**
    * CREATE TABLE test.searchrequests (id uuid PRIMARY KEY , loginDetails text, searchDetails text, roomRequests list<text>);
    */
  final val requestTable = CassandraDatabase.requests
}
