package utils

import java.util.UUID

import com.outworkers.phantom.dsl.UUID

/**
  * Created by fayaz on 30.05.17.
  */
object Implicits {
  implicit def str2uuid(str: String): UUID = UUID.fromString(str)
}
