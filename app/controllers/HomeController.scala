package controllers

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import javax.inject.Inject
import models.Pokemon
import models.PokemonDetails.Details
import play.api.data._
import play.api.mvc._
import play.api.libs.ws._
import play.api.libs.ws.ahc.AhcWSClient
import services.PokemonService

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, Future}
import PokemonStatusForm.{StatusData, statusform}
import net.liftweb.json.DefaultFormats
import scalaj.http.Http
import net.liftweb.json.Serialization.write
import tools.Implicits



class HomeController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc)
  with Implicits {
  import PokemonForm._

  private val postUrl = routes.DetailController.findPokemon()

  def index = Action {
    Ok(views.html.index())
  }

  def home = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.home(form, postUrl))
  }


}
