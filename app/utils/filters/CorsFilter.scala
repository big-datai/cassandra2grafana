package utils.filters

import com.google.inject.Inject
import play.api.http.DefaultHttpFilters
import play.filters.cors.CORSFilter

/**
  * Created by fayaz on 31.05.17.
  */
class CorsFilter @Inject() (corsFilter: CORSFilter) extends DefaultHttpFilters(corsFilter)