package tools

import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.{FiniteDuration, MILLISECONDS}

object AppSettings {

  val appConfiguration = ConfigFactory.load()

  object Client {

    val connectTimeout = appConfiguration.getDuration("common.http.client.connect-timeout",MILLISECONDS).toInt
    val readTimeout = appConfiguration.getDuration("common.http.client.read-timeout",MILLISECONDS).toInt
    val cacheSize = appConfiguration.getInt("common.http.cache.max-size")
    val cacheTTl =FiniteDuration(
      appConfiguration.getDuration("common.http.cache.ttl",MILLISECONDS),
      MILLISECONDS)
  }

  object PokemonServiceConf {
    val path = appConfiguration.getString("common.pokemonService.path")

  }

  object PokemonAkkaAPI {
    val path = appConfiguration.getString("common.PokemonAkkaApi.path")
  }


}
