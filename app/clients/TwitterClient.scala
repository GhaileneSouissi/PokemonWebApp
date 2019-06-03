//package clients
//
//import org.apache.spark.streaming._
//import org.apache.spark.streaming.twitter._
////import Utilities._
//
//object TwitterClient {
//  System.setProperty("twitter4j.oauth.consumerKey", "rRG3MdSzJa4CsPmgk9bzfFCxi")
//  System.setProperty("twitter4j.oauth.consumerSecret", "JehiRA661LnwTKegNRSIHwsglEvUVjqmMshb0fSN4j6fbHziXo")
//  System.setProperty("twitter4j.oauth.accessToken", "1133384270033166339-HVx2UfQOKfvD0V7sI9KtITFSlqtQUO")
//  System.setProperty("twitter4j.oauth.accessTokenSecret", "Zz9hqTFjyVX5kC71kEDrhNXtkyOQsMWjEtoDnvQ7UfahX")
//
//  val ssc = new StreamingContext("local[1]", "PrintTweets", Seconds(1))
//
//  val filters = Array("test")
//  val tweets = TwitterUtils.createStream(ssc, None, filters)
//  val statuses = tweets.map(status => status.getText())
//  statuses.print()
//
//  ssc.start()
//  ssc.awaitTermination()
//}
