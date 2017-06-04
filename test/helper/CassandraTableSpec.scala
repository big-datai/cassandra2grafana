package helper

import com.outworkers.phantom.dsl.ResultSet
import helper.EmbeddedCassandra.EmbeddedCassandraLike
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures

/**
  * Created by fayaz on 29.05.17.
  */
trait CassandraTableSpec
  extends FlatSpec
  with Matchers
  with Inspectors
  with ScalaFutures
  with OptionValues
  with BeforeAndAfterAll
  with Samples
  with EmbeddedCassandraLike
  with utils.Connection.testConnector.Connector {

  def checks(resultSet: ResultSet): Assertion = {
    resultSet isExhausted() shouldBe true
    resultSet wasApplied() shouldBe true
  }
}
