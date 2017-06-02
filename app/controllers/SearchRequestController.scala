package controllers

import databases.Tables._
import forms.ValidationForms._
import play.api.Logger.logger
import play.api.libs.json.Json
import play.api.mvc._
import utils.Implicits._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class SearchRequestController extends Controller {

  def testConnection = Action { Ok }

  def save = Action.async(parse.form(searchRequestInsertForm)) { implicit requests =>
    logger.info("Saving to table...")
    searchRequestsTable.save(requests.body).map(_ => Created)
  }

  def find(id: String) = Action.async {
    logger.info(s"Getting request by id: $id")
    searchRequestsTable.find(id) map {
      case Some(sr) => Ok(Json.toJson(sr))
      case None => NoContent
    }
  }

  def update(id: String) = Action.async(parse.form(searchRequestUpdateForm)) { implicit requests =>
    logger.info(s"Updating request by id: $id")
    searchRequestsTable.modify(id, requests.body) map(_ => Ok)
  }

  def remove(id: String) = Action.async {
    logger.info(s"Remove request by id: $id")
    searchRequestsTable.remove(id) map(_ => Ok)
  }
}
