package com.jactravel.utils

/**
  * Created by fayaz on 17.06.17.
  */
object Extension {
  implicit class RichString(str: String) {
    def toSeconds: Int = str.split("s")(0).toInt
  }
}
