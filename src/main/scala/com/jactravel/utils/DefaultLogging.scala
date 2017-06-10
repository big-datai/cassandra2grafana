package com.jactravel.utils

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

/**
  * Created by fayaz on 09.06.17.
  */
trait DefaultLogging {
  final val log: Logger = Logger(LoggerFactory.getLogger(this.getClass))
}
