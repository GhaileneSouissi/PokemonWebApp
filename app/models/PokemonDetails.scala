package models

import java.net.URL


object  PokemonDetails {

  case class stat(name: String, baseStat: Int)


  case class Details(name: String = "", types: Seq[String]= Nil, stats: Seq[stat] = Nil, picture: URL = new URL("https://www.site.com"))

}