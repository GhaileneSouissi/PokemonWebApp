package services

import java.net.URL
import java.util.concurrent.TimeUnit

import com.google.common.cache.{Cache, CacheBuilder}
import models.PokemonDetails
import models.PokemonDetails.{Details, stat}
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import scalaj.http.Http
import utils.AppSettings

import scala.concurrent.duration.{FiniteDuration, _}
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps
import scala.reflect.ClassTag
import utils.AppSettings.Client._
import net.liftweb.json._
import net.liftweb.json.Serialization.write

/**
  * A cache for the Poke Service API, it compute new values and stock it in a non permanent memory
  * @tparam Key
  * @tparam Details
  */
trait Service[Key <: String, Details <: PokemonDetails.Details] {
  final val JsonMimeType = "application/json"
  protected[this] val acceptHeader: (String, String) =
    "Accept" -> s"$JsonMimeType"

  protected[this] val cacheMaxSize: Int = cacheSize
  protected[this] val cacheTTL: FiniteDuration = cacheTTl
  protected[this] val cache: Cache[Key, Option[PokemonDetails.Details]] = CacheBuilder
    .newBuilder()
    .maximumSize(cacheMaxSize)
    .expireAfterWrite(cacheTTL.toMillis, TimeUnit.MILLISECONDS)
    .build[Key, Option[PokemonDetails.Details]]()

  def path(key: Key): String


  private def url(key: Key): String = s"https://pokeapi.co/api/v2/pokemon/${key}"

  protected[this] def compute(key: Key)
                             (implicit tag: ClassTag[PokemonDetails.Details],
                              context: ExecutionContext): Future[Option[PokemonDetails.Details]] = {
    val request = Http(url(key))
      .headers(acceptHeader)
      .timeout(connectTimeout, readTimeout)

    Future.successful(request.asBytes)
      .map { response =>
        response.code match {
          // Status 200: Success
          case 200 =>
            bodyAsPokemonDetails(response.contentType,response.body)

          // Status 404: Not found
          case 404 =>
            //println(s"Service responded 404 for ${request.url}") //TODO; replace println by logger
            None

          // Status 415: Unsupported media type
          case 415 =>
            //println(s"Unsupported type $acceptHeader for ${request.url}")
            None

          case code =>
            throw new IllegalArgumentException(
              s"Service responded $code for ${request.url}"
            )
        }
      }
  }

  def find(key: Key)(implicit tag: ClassTag[PokemonDetails.Details],
                     context: ExecutionContext): Future[Option[PokemonDetails.Details]] =
    cache.getIfPresent(key) match {
      case value @ (Some(_) | None) =>
        Future.successful(value)
      case _ =>
        compute(key)
          .map { maybeValue =>
            cache.put(key, maybeValue)
            maybeValue
          }
    }

  private def read(response: JsValue): PokemonDetails.Details = {
    val name = (response \ "name").as[String]
    val types = ((response \ "types").as[JsValue] \\ "type" map (_ \ "name"))
      .map(_.validate[String])
      .map{
        case JsSuccess(x,_) => x
        case JsError(_) => ""
      }
    val statName = ((response \ "stats").as[JsValue] \\ "stat" map (_ \ "name"))
      .map(_.validate[String])
      .map{
        case JsSuccess(x,_) => x
        case JsError(_) => ""

      }

    val base_stat = ((response \ "stats").as[JsValue] \\ "base_stat")
      .map(_.validate[Int])
      .map{
        case JsSuccess(x,_) => x
        case JsError(_) => 0
      }

    val stats = statName
      .zip(base_stat)
      .map(x => stat(x._1,x._2))

    val sprite = ((response \ "sprites").as[JsValue] \ "front_default").as[String]

    Details(
      name = name,
      types = types,
      stats = stats,
      picture = sprite
    )
  }

  private[this] def bodyAsPokemonDetails(contentType: Option[String], body: Array[Byte])
                                        (implicit tag: ClassTag[PokemonDetails.Details]): Option[PokemonDetails.Details] =


    contentType.flatMap{_ =>
      val jsonString = body.map(_.toChar).mkString
      Some(read(Json.parse(jsonString)))
    }


}

