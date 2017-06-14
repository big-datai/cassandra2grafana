package com.jactravel.utils

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.jactravel.databases.entity.BookRoomInfo
import spray.json.DefaultJsonProtocol

import scala.language.implicitConversions

/**
  * Created by fayaz on 14.06.17.
  */
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val bookInfoFmt = jsonFormat7(BookRoomInfo)
}