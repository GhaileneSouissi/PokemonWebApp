package model
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import models.PokemonDetails.{Details, stat}
import spray.json._

object PokemonDetailJson extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val statFormat = jsonFormat2(stat)
  implicit val PortofolioFormats = jsonFormat4(Details)
}

