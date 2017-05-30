package controllers

import java.util.UUID

import com.google.inject.Inject
import com.outworkers.phantom.dsl.UUID
import databases.Tables
import forms.ValidationForms._
import model.SearchRequest._
import play.api.libs.json.Json
import play.api.mvc._
import utils.CustomLogger

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class ApplicationController @Inject()(tables: Tables) extends Controller with CustomLogger {

  implicit def str2uuid(str: String): UUID = UUID.fromString(str)

  def testConnection = Action { Ok }

  def save = Action.async(parse.form(insertForm)) { implicit requests =>
    tables.requestTable.save(requests.body).map(_ => Created)
  }

  def find(id: String) = Action.async {
    tables.requestTable.find(id) map {
      case Some(sr) => Ok(Json.toJson(sr))
      case None => NoContent
    }
  }

  def update(id: String) = Action.async(parse.form(updateForm)) { implicit requests =>
    tables.requestTable.modify(id, requests.body) map(_ => Ok)
  }

  def remove(id: String) = Action.async {
    tables.requestTable.remove(id) map(_ => Ok)
  }
}
