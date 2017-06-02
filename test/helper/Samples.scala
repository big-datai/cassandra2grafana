package helper

import com.datastax.driver.core.utils.UUIDs
import databases.model.SearchRequest
import org.joda.time.DateTime

/**
  * Created by fayaz on 30.05.17.
  */
trait Samples {
  final val sampleSearchRequest = SearchRequest(
    id = UUIDs.timeBased(),
    login = "testLogin",
    password = "testPass",
    currencyId = 3,
    arrival = DateTime.now(),
    duration = 2,
    regionId = 5,
    mealBasisId = 0,
    minStarRating = 0,
    adults = 2,
    children = 0,
    childrenAges = 0
  )
}
