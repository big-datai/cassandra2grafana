package forms

import databases.model._
import play.api.data.Form
import play.api.data.Forms._

/**
  * Created by fayaz on 29.05.17.
  */
object ValidationForms {

  final val searchRequestInsertForm = Form(
    mapping(
      "id" -> uuid,
      "login" -> nonEmptyText,
      "password" -> nonEmptyText,
      "currencyId" -> number,
      "arrival" -> jodaDate,
      "duration" -> number,
      "regionId" -> number,
      "mealBasisId" -> number,
      "minStarRating" -> number,
      "adults" -> number,
      "children" -> number,
      "childrenAges" -> number
    )(SearchRequest.apply)(SearchRequest.unapply)
  )

  final val searchRequestUpdateForm = Form(
    mapping(
      "login" -> nonEmptyText,
      "password" -> nonEmptyText,
      "currencyId" -> number,
      "arrival" -> jodaDate,
      "duration" -> number,
      "regionId" -> number,
      "mealBasisId" -> number,
      "minStarRating" -> number,
      "adults" -> number,
      "children" -> number,
      "childrenAges" -> number
    )(SearchRequestUpdate.apply)(SearchRequestUpdate.unapply)
  )
}
