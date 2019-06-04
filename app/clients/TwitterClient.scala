package clients

import scalaj.http.{Http, Token}

/**
  * A twitter client , it authenticates and then start reading tweets using twitter api.
  */
object TwitterClient {

  private val consumer = Token("rRG3MdSzJa4CsPmgk9bzfFCxi", "JehiRA661LnwTKegNRSIHwsglEvUVjqmMshb0fSN4j6fbHziXo")
  private val accessToken = Token("1133384270033166339","Zz9hqTFjyVX5kC71kEDrhNXtkyOQsMWjEtoDnvQ7UfahX")

  def readTweets(pokemonName: String) = {
    val response = Http(url = "https://stream.twitter.com/1.1/statuses/filter.json?track="+pokemonName).
      postForm(Seq("oauth_callback" -> "oob"))
      .oauth(consumer,accessToken).asToken

    response.contentType //TODO ; verify content type

  }

}