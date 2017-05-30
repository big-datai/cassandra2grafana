package controllers

import databases.Tables.requestTable
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
class ApplicationController extends Controller {

  def testConnection = Action { Ok }

  def save = Action.async(parse.form(insertForm)) { implicit requests =>
    logger.info("Saving to table...")
    requestTable.save(requests.body).map(_ => Created)
  }

  def find(id: String) = Action.async {
    logger.info(s"Getting request by id: $id")
    requestTable.find(id) map {
      case Some(sr) => Ok(Json.toJson(sr))
      case None => NoContent
    }
  }

  def update(id: String) = Action.async(parse.form(updateForm)) { implicit requests =>
    logger.info(s"Updating request by id: $id")
    requestTable.modify(id, requests.body) map(_ => Ok)
  }

  def remove(id: String) = Action.async {
    logger.info(s"Remove request by id: $id")
    requestTable.remove(id) map(_ => Ok)
  }
}
