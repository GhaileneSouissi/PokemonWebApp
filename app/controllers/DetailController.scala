package controllers

import controllers.PokemonStatusForm.statusform
import javax.inject.Inject
import models.Pokemon
import models.PokemonDetails.Details
import play.api.data.Form
import play.api.mvc.{AnyContent, MessagesAbstractController, MessagesControllerComponents, MessagesRequest}
import services.PokemonService
import tools.Implicits

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class DetailController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc)
  with Implicits{
  import PokemonForm._

  private val postCommentUrl = routes.CommentController.commentPokemon()
  private val postUrl = routes.DetailController.findPokemon()



  def listDetails(detail: Details) = Action { implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.detail(detail,statusform,postCommentUrl))
  }

  def findPokemon = Action { implicit request: MessagesRequest[AnyContent] =>
    val errorFunction = { formWithErrors: Form[Data] =>
      BadRequest(views.html.home(formWithErrors, postUrl))
    }

    val successFunction = { data: Data =>
      val pokemon = Pokemon(name = data.name)
      Await.result(PokemonService.find(pokemon.name),5 seconds) match {
        case Some(res) => Ok(views.html.detail(res,statusform,postCommentUrl))
        case _ =>  Ok("Pokemon Not found !!!")
      }
    }
    form.bindFromRequest.fold(errorFunction, successFunction)

  }
}
