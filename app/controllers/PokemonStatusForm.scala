package controllers
import play.api.data.Forms._
import play.api.data.Form

/**
  * mapping of the comments sent by users
  */

object PokemonStatusForm {

  case class StatusData(name: String = "", like: Boolean = false, dislike: Boolean = false, comment: String = "")

  val statusform = Form(
    mapping(
      "name" -> nonEmptyText,
      "like" -> boolean,
      "dislike" -> boolean,
      "comment" -> text
    )(StatusData.apply)(StatusData.unapply)
  )
}
