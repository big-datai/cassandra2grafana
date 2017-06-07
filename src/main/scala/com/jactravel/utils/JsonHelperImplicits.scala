package com.jactravel.utils

import spray.json.{DefaultJsonProtocol, JsValue}

import scala.language.implicitConversions

/**
  * Created by fayaz on 07.06.17.
  */
trait JsonHelperImplicits extends DefaultJsonProtocol {

  implicit def jsValue2String(jsValue: JsValue): String = jsValue.convertTo[String]

  implicit def jsValue2Int(jsValue: JsValue): Int = jsValue.convertTo[Int]

  implicit def jsValue2ListInt(jsValue: JsValue): List[Int] = jsValue.convertTo[List[Int]]
}
