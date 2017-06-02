package databases.model

import java.util.UUID

import com.outworkers.phantom.dsl.DateTime
import play.api.libs.json.Json

/**
  * Created by fayaz on 29.05.17.
  */
case class SearchRequest(id: UUID = UUID.randomUUID(),
                         login: String,
                         password: String,
                         currencyId: Int,
                         arrival: DateTime,
                         duration: Int,
                         regionId: Int,
                         mealBasisId: Int,
                         minStarRating: Int,
                         adults: Int,
                         children: Int,
                         childrenAges: Int)

case class SearchRequestUpdate(login: String,
                               password: String,
                               currencyId: Int,
                               arrival: DateTime,
                               duration: Int,
                               regionId: Int,
                               mealBasisId: Int,
                               minStarRating: Int,
                               adults: Int,
                               children: Int,
                               childrenAges: Int)

object SearchRequest {
  implicit val searchRequestsFmt = Json.format[SearchRequest]
}
