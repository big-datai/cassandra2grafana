package controllers

import java.util.UUID

import com.outworkers.phantom.dsl.UUID
import databases.Databases._
import forms.ValidationForms._
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

  def save = Action.async(parse.form(insertForm)) { implicit requests =>
    requestsTable.save(requests.body).map(_ => Created)
  }

  def find(id: String) = Action.async {
    requestsTable.find(id) map {
      case Some(sr) => Ok(Json.toJson(sr))
      case None => NoContent
    }
  }

  def update(id: String) = Action.async(parse.form(updateForm)) { implicit requests =>
    requestsTable.modify(id, requests.body) map(_ => Ok)
  }

  def remove(id: String) = Action.async {
    requestsTable.remove(id) map(_ => Ok)
  }
}
