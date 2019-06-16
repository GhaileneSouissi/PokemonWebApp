package utils

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Directives.{complete, get, onComplete, parameters, path}
import model.PokemonComment
import net.liftweb.json.Serialization.write
import services.PokemonService
import scalaj.http.{Http => HttpClient}
import model.PokemonCommentJson._

import scala.util.{Failure, Success}

trait Routes extends Implicits {

  val route =
    path("search") {
      get {
        parameters('name) { name =>
          onComplete(PokemonService.find(name)){
            case Success(value) =>
              val jsonString = value match {
                case Some(pokemon) => write(pokemon)
                case _ => """{"error":"pokemon was not found !!, please try again"}"""
              }
              HttpClient("http://localhost:9001/save").postData(jsonString).header("content-type", "application/json").asString
              complete(HttpEntity(ContentTypes.`application/json`,jsonString))

            case Failure(ex) => complete(s"An error occurred: ${ex.getMessage}")
          }
        }
      }
    } ~
      path("read") {
        get {
          parameters('name) { name =>
            val response = HttpClient("http://localhost:9001/read").param("name", name).asString
            complete(HttpEntity(ContentTypes.`application/json`, response.body))
          }
        }
      }~
          path("comment") {
            post {
              entity(as[PokemonComment]) { comment =>
                val jsonString = write(comment)
                HttpClient("http://localhost:9001/comment").postData(jsonString).header("content-type", "application/json").asString
                complete(HttpEntity(ContentTypes.`application/json`, jsonString))

              }
            }
          }

}
