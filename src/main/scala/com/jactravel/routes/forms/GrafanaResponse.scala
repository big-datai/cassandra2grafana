package com.jactravel.routes.forms

import com.jactravel.utils.Types.TimeSeries

/**
  * Created by fayaz on 22.06.17.
  */
case class GrafanaResponse(target: String, datapoints: List[TimeSeries] = Nil)
