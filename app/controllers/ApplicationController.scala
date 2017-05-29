package controllers

import java.util.UUID

import com.outworkers.phantom.dsl.UUID
import databases.Databases._
import forms.ValidationForms.searchRequestForm
import play.api.libs.json.Json
import play.api.mvc._
import model.SearchRequest._
import utils.CustomLogger
import model._
import org.joda.time.DateTime

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class ApplicationController extends Controller with CustomLogger {

  implicit def str2uuid(str: String): UUID = UUID.fromString(str)

  def testConnection = Action { Ok }

  def save = Action.async(parse.form(searchRequestForm)) { implicit requests =>
    requestsTable.save(requests.body).map(_ => Created)
  }

  def find(id: String) = Action.async {
    requestsTable.find(id) map {
      case Some(sr) => Ok(Json.toJson(sr))
      case None => NoContent
    }
  }

  def remove(id: String) = Action.async {
    requestsTable.remove(id) map(_ => NoContent)
  }

//  def testSave = Action.async {
//    log.info("testSaving")
//    requestsTable.save(SearchRequest(
//      loginDetails = LoginDetails("fsanaulla  ", "1234", 4),
//      searchDetails = SearchDetails(DateTime.now, 0, 0, 0, 0),
//      roomRequests = List(RoomRequest(2, 0, 0)))
//    ) map { res => Ok(s"${res.wasApplied()}")}
//  }
}
