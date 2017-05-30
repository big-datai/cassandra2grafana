package table

import helper.CassandraTableSpec
import model._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

/**
  * Created by fayaz on 29.05.17.
  */
class SearchRequestsSpec extends CassandraTableSpec {

  override def beforeAll(): Unit = database.create()
  val table = database.requests

  "SearchRequest dao" should "work correctly" in {
    for {
      insert <- table.save(sample)
      update <- table.modify(
        sample.id,
        SearchRequestUpdate(
          LoginDetails("login", "testPass", 3),
          sample.searchDetails,
          sample.roomRequests))
      find <- table.find(sample.id)
      remove <- table.remove(sample.id)
      check <- table.find(sample.id)
    } yield {
      checks(insert)
      checks(update)
      find.map(_.loginDetails.login) shouldBe Some("login")
      checks(remove)
      check shouldBe None
    }
  }
}
