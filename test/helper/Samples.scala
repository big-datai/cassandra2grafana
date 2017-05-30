package helper

import com.datastax.driver.core.utils.UUIDs
import databases.model.{LoginDetails, RoomRequest, SearchDetails, SearchRequest}
import org.joda.time.DateTime

/**
  * Created by fayaz on 30.05.17.
  */
trait Samples {
  final val sample = SearchRequest(
    UUIDs.timeBased(),
    loginDetails = LoginDetails("testLogin", "testPass", 3),
    searchDetails = SearchDetails(DateTime.now(), 2, 5, 0, 0),
    roomRequests = List(RoomRequest(2, 0, 0))
  )
}
