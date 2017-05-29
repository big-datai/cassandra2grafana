package model

import java.util.UUID

import com.outworkers.phantom.dsl.DateTime
import play.api.libs.json.Json

/**
  * Created by fayaz on 29.05.17.
  */
case class LoginDetails(login: String,
                        password: String,
                        currencyId: Int)

case class SearchDetails(arrival: DateTime,
                         duration: Int,
                         regionId: Int,
                         mealBasisId: Int,
                         minStarRating: Int)

case class RoomRequest(adults: Int,
                       children: Int,
                       childrenAges: Int)

case class SearchRequest(id: UUID = UUID.randomUUID(),
                         loginDetails: LoginDetails,
                         searchDetails: SearchDetails,
                         roomRequests: List[RoomRequest])

case class SearchRequestUpdate(loginDetails: LoginDetails,
                               searchDetails: SearchDetails,
                               roomRequests: List[RoomRequest])

object SearchRequest {
  implicit val loginFmt = Json.format[LoginDetails]
  implicit val searchFmt = Json.format[SearchDetails]
  implicit val roomFmt = Json.format[RoomRequest]
  implicit val searchRequestsFmt = Json.format[SearchRequest]
}
