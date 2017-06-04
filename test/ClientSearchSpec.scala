import helper.CassandraTableSpec

import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

/**
  * Created by fayaz on 29.05.17.
  */
class ClientSearchSpec extends CassandraTableSpec {

  override def beforeAll(): Unit = database.create()
  val table = database.clientSearch

  "SearchRequests table" should "find/remove correctly" in {
    for {
      insert <- table.insert
        .value(_.SearchQueryUUID, sample.SearchQueryUUID)
        .value(_.ClientIP, sample.ClientIP)
        .value(_.Host, sample.Host)
        .value(_.ClientRequestTimestamp, sample.ClientRequestTimestamp)
        .value(_.ClientResponseTimestamp, sample.ClientResponseTimestamp)
        .value(_.ForwardedRequestTimestamp, sample.ForwardedRequestTimestamp)
        .value(_.ForwardedResponseTimestamp, sample.ForwardedResponseTimestamp)
        .value(_.TradeID, sample.TradeID)
        .value(_.BrandID, sample.BrandID)
        .value(_.SalesChannelID, sample.SalesChannelID)
        .value(_.GeographyLevel1ID, sample.GeographyLevel1ID)
        .value(_.GeographyLevel2ID, sample.GeographyLevel2ID)
        .value(_.GeographyLevel3ID, sample.GeographyLevel3ID)
        .value(_.PropertyID, sample.PropertyID)
        .value(_.PropertyReferenceID, sample.PropertyReferenceID)
        .value(_.ArrivalDate, sample.ArrivalDate)
        .value(_.Duration, sample.Duration)
        .value(_.Rooms, sample.Rooms)
        .value(_.Adults, sample.Adults)
        .value(_.Children, sample.Children)
        .value(_.ChildAges, sample.ChildAges)
        .value(_.MealBasisID, sample.MealBasisID)
        .value(_.MinStarRating, sample.MinStarRating)
        .value(_.HotelCount, sample.HotelCount)
        .value(_.Success, sample.Success)
        .value(_.ErrorMessage, sample.ErrorMessage)
        .value(_.SuppliersSearched, sample.SuppliersSearched)
        .value(_.RequestXML, sample.RequestXML)
        .value(_.ResponseXML, sample.ResponseXML)
        .future()
      find <- table.find(sample.SearchQueryUUID)
      remove <- table.remove(sample.SearchQueryUUID)
      check <- table.find(sample.SearchQueryUUID)
    } yield {
      checks(insert)
      find.map(_.SearchQueryUUID) shouldBe Some(sample.SearchQueryUUID)
      checks(remove)
      check shouldBe None
    }
  }
}
