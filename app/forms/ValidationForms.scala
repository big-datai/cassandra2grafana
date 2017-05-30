package forms

import databases.model._
import play.api.data.Form
import play.api.data.Forms._

/**
  * Created by fayaz on 29.05.17.
  */
object ValidationForms {

  final val insertForm = Form(
    mapping(
      "id" -> uuid,
      "loginDetails" -> mapping(
        "login" -> nonEmptyText,
        "password" -> nonEmptyText,
        "currencyId" -> number
      )(LoginDetails.apply)(LoginDetails.unapply),
      "searchDetails" -> mapping(
        "arrival" -> jodaDate,
        "duration" -> number,
        "regionId" -> number,
        "mealBasisId" -> number,
        "minStarRating" -> number
      )(SearchDetails.apply)(SearchDetails.unapply),
      "roomsRequests" -> list(mapping(
        "adults" -> number,
        "children" -> number,
        "childrenAges" -> number
      )(RoomRequest.apply)(RoomRequest.unapply))
    )(SearchRequest.apply)(SearchRequest.unapply)
  )

  final val updateForm = Form(
    mapping(
      "loginDetails" -> mapping(
        "login" -> nonEmptyText,
        "password" -> nonEmptyText,
        "currencyId" -> number
      )(LoginDetails.apply)(LoginDetails.unapply),
      "searchDetails" -> mapping(
        "arrival" -> jodaDate,
        "duration" -> number,
        "regionId" -> number,
        "mealBasisId" -> number,
        "minStarRating" -> number
      )(SearchDetails.apply)(SearchDetails.unapply),
      "roomsRequests" -> list(mapping(
        "adults" -> number,
        "children" -> number,
        "childrenAges" -> number
      )(RoomRequest.apply)(RoomRequest.unapply))
    )(SearchRequestUpdate.apply)(SearchRequestUpdate.unapply)
  )
}
