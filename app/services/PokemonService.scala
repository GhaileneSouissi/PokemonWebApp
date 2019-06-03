package services

import models.PokemonDetails.Details
import tools.AppSettings.PokemonServiceConf

object PokemonService extends Service[String,Details]{


  def path(name:String): String = {
    val path = PokemonServiceConf.path
    path.replaceAllLiterally(name,":name")
  }


}
