package utils

import play.api.Logger

/**
  * Created by fayaz on 29.05.17.
  */
trait CustomLogger {
  final val log = Logger("app")
}
