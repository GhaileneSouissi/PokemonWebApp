package services

import models.PokemonDetails.Details
import tools.AppSettings.PokemonServiceConf

/**
  * A pokemon service , it calls the cache
  */
object PokemonService extends Service[String,Details]{


  def path(name:String): String = {
    val path = PokemonServiceConf.path
    path.replaceAllLiterally(name,":name")
  }


}
