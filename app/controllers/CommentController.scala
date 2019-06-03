package controllers

import controllers.PokemonStatusForm.{StatusData, statusform}
import javax.inject.Inject
import models.Pokemon
import models.PokemonDetails.Details
import net.liftweb.json.DefaultFormats
import net.liftweb.json.Serialization.write
import play.api.data.Form
import play.api.mvc.{AnyContent, MessagesAbstractController, MessagesControllerComponents, MessagesRequest}
import scalaj.http.Http
import tools.AppSettings.PokemonAkkaAPI.path

class CommentController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc){

  private val postCommentUrl = routes.CommentController.commentPokemon()


  def commentPokemon = Action { implicit request: MessagesRequest[AnyContent] =>

    val errorFunction = { formWithErrors: Form[StatusData] =>
      BadRequest(views.html.detail(Details(), formWithErrors, postCommentUrl))
    }

    val successFunction = { data: StatusData =>
      val pokemon = Pokemon(name = data.name)
      implicit val formats = DefaultFormats
      val jsonString = write(data)

      Http(path).postData(jsonString).header("content-type", "application/json").asString.code match {
        case 200 => Ok("Comment sucessfully added..")
        case _ => Ok("Error")
      }
    }
    statusform.bindFromRequest.fold(errorFunction, successFunction)

  }

}
