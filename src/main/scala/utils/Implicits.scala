package utils

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import net.liftweb.json.DefaultFormats

trait Implicits {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher
  implicit val formats = DefaultFormats

}
