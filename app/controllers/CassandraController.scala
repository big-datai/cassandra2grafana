package controllers

import java.util.UUID

import databases.CassandraDatabase
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
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action {
    Ok("Hello")
  }

  def save(fname: String, lname: String) = Action.async {
    CassandraDatabase.users.save(User(UUID.randomUUID(), fname, lname, DateTime.now())) map { result =>
      Ok(s"${result.wasApplied()}")
    }
  }
}
