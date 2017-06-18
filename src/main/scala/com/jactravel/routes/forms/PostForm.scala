package com.jactravel.routes.forms

import org.joda.time.DateTime

/**
  * Created by fayaz on 17.06.17.
  */
case class RangeForm(from: DateTime, to: DateTime)

case class TargetForm(target: String, refId: String, `type`: String)

case class PostForm(range: RangeForm,
                    intervalMs: Long,
                    targets: List[TargetForm],
                    format: String,
                    maxDataPoints: Int)

