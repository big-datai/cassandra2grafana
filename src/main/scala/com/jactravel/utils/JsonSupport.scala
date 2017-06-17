package com.jactravel.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.jactravel.databases.entity.BookRoomInfo
import com.jactravel.routes.forms.{PostForm, RangeForm, TargetForm}
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import spray.json._

import scala.language.implicitConversions

/**
  * Created by fayaz on 14.06.17.
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {

  implicit def str2JsString(str: String): JsString = JsString(str)

  implicit def str2date(time: String): DateTime = DateTimeFormat.forPattern(Constants.FULL_DATE_PATTERN).parseDateTime(time)


  implicit val rangeFmt: RootJsonFormat[RangeForm] = jsonFormat2(RangeForm)

  implicit val targetFmt: RootJsonFormat[TargetForm] = jsonFormat2(TargetForm)

  implicit val postFmt: RootJsonFormat[PostForm] = jsonFormat5(PostForm)

  implicit val bookInfoFmt: RootJsonFormat[BookRoomInfo] = jsonFormat7(BookRoomInfo)

  implicit object DateTimeJsonFormat extends RootJsonFormat[DateTime] {
    override def read(json: JsValue): DateTime = json match {
      case JsString(str) => DateTime.parse(str)
      case _ => throw DeserializationException(s"Can't deserialize $json to DateTime")
    }

    override def write(obj: DateTime): JsValue = JsString(obj.toString())
  }
}