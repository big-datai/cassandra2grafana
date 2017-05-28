package controllers

import java.util.UUID

import databases.Databases._
import entity.User
import org.joda.time.DateTime
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
class CassandraController extends Controller {
  //todo: Specify methods
  def testConnection = Action {
    Ok("Hello")
  }

  def save(fname: String, lname: String) = Action.async {
    userTable.save(User(UUID.randomUUID(), fname, lname, DateTime.now())) map { result =>
      Ok(s"${result.wasApplied()}")
    }
  }
}
