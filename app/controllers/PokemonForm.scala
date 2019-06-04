package controllers
import play.api.data.Forms._
import play.api.data.Form

/**
  * mapping of pokemon form (just a name)
  */
object PokemonForm {

  case class Data(name: String)

  val form = Form(
    mapping(
      "name" -> nonEmptyText
    )(Data.apply)(Data.unapply)
  )
}
