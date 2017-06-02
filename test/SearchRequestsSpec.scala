import databases.model._
import helper.CassandraTableSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

/**
  * Created by fayaz on 29.05.17.
  */
class SearchRequestsSpec extends CassandraTableSpec {

  override def beforeAll(): Unit = database.create()
  val table = database.searchRequests

  "SearchRequests table" should "insert/update/find/remove correctly" in {
    for {
      insert <- table.save(sampleSearchRequest)
      update <- table.modify(
        sampleSearchRequest.id,
        SearchRequestUpdate(
          sampleSearchRequest.login,
          sampleSearchRequest.password,
          sampleSearchRequest.currencyId,
          sampleSearchRequest.arrival,
          sampleSearchRequest.duration,
          sampleSearchRequest.regionId,
          sampleSearchRequest.mealBasisId,
          sampleSearchRequest.minStarRating,
          sampleSearchRequest.adults,
          sampleSearchRequest.children,
          sampleSearchRequest.childrenAges
        ))
      find <- table.find(sampleSearchRequest.id)
      remove <- table.remove(sampleSearchRequest.id)
      check <- table.find(sampleSearchRequest.id)
    } yield {
      checks(insert)
      checks(update)
      find.map(_.login) shouldBe Some("login")
      checks(remove)
      check shouldBe None
    }
  }
}
