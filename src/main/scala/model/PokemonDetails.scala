package models


object  PokemonDetails {

  case class stat(name: String, baseStat: Int)
  case class Details(name: String = "",types: Seq[String]= Nil, stats: Seq[stat] = Nil, picture: String = "")

}


