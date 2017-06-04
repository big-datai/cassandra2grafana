package controllers

import databases.Tables._
import play.api.Logger.logger
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class ClientSearchController extends Controller {

  def testConnection = Action { Ok }

  def find(id: String) = Action.async {
    logger.info(s"Getting request by id: $id")
    clientSearchTable.find(id) map {
      case Some(cs) => Ok(Json.toJson(cs))
      case None => NoContent
    }
  }

  def remove(id: String) = Action.async {
    logger.info(s"Remove request by id: $id")
    clientSearchTable.remove(id) map(_ => Ok)
  }
}
