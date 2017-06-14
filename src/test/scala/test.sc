
import spray.json.DefaultJsonProtocol._
import spray.json._
implicit val fmt = jsonFormat2(Test)
case class Test(name: String, lname: String)
val t = Test("Fayaz", "Sanaulla")
val obj = JsObject(
  "name" -> JsString("Fyaz"),
  "lname" -> JsString("Sanaulla")
)

obj.convertTo[Test]


