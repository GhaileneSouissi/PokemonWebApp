package controllers
import play.api.data.Forms._
import play.api.data.Form

object PokemonForm {

  case class Data(name: String)

  val form = Form(
    mapping(
      "name" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )
}
