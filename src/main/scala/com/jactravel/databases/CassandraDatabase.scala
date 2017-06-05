package databases

import com.outworkers.phantom.database.Database
import com.outworkers.phantom.dsl.KeySpaceDef
import databases.table.ClientSearch
import utils.Connection

/**
  * Created by fayaz on 27.05.17.
  */
class CassandraDatabase(val keySpace: KeySpaceDef) extends Database[CassandraDatabase](keySpace) {
  object clientSearch extends ClientSearch with Connector
}

object Tables {
  object CassandraDatabase extends CassandraDatabase(Connection.connector)

  final val clientSearchTable = CassandraDatabase.clientSearch
}
