package com.jactravel.routes.forms

import org.joda.time.DateTime

/**
  * Created by fayaz on 17.06.17.
  */
case class RangeForm(from: DateTime, to: DateTime)

case class TargetForm(refId: String, target: String)

case class PostForm(range: RangeForm,
                    interval: String,
                    targets: List[TargetForm],
                    format: String,
                    maxDataPoints: Int)

