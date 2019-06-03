package tools

import com.typesafe.config.ConfigFactory

object AppSettings {

  val appConfiguration = ConfigFactory.load()

  object Client {

    val connectTimeout = appConfiguration.getString("common.http.client.connect-timeout").replaceAll("[^0-9]", "").toInt
    val readTimeout = appConfiguration.getString("common.http.client.read-timeout").replaceAll("[^0-9]", "").toInt
    val cacheSize = appConfiguration.getInt("common.http.cache.max-size")
    val cacheTTl = appConfiguration.getString("common.http.cache.ttl")
  }

  object PokemonServiceConf {
    val path = appConfiguration.getString("common.pokemonService.path")

  }

  object PokemonAkkaAPI {
    val path = appConfiguration.getString("common.PokemonAkkaApi.path")
  }


}
