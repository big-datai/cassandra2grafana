package com.jactravel.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.jactravel.databases.entity.BookRoomInfo
import com.jactravel.routes.forms.{PostForm, RangeForm, TargetForm}
import org.joda.time.DateTime
import spray.json._

import scala.language.implicitConversions

/**
  * Created by fayaz on 14.06.17.
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit def str2date(time: String): DateTime = DateTime.parse(time)

  implicit object DateTimeJsonFormat extends RootJsonFormat[DateTime] {
    override def read(json: JsValue): DateTime = json match {
      case JsString(str) => str
      case _ => throw DeserializationException(s"Can't deserialize $json to DateTime")
    }

    override def write(obj: DateTime): JsValue = JsString(obj.toString())
  }

  implicit def str2JsString(str: String): JsString = JsString(str)

  implicit val rangeFmt: RootJsonFormat[RangeForm] = jsonFormat2(RangeForm)

  implicit val targetFmt: RootJsonFormat[TargetForm] = jsonFormat3(TargetForm)

  implicit val postFmt: RootJsonFormat[PostForm] = jsonFormat5(PostForm)

  implicit val bookInfoFmt: RootJsonFormat[BookRoomInfo] = jsonFormat7(BookRoomInfo)
}