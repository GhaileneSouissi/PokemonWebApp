package tools

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws.ahc.AhcWSClient

trait Implicits {
  implicit val system = ActorSystem("pokemon-system")
  implicit val materializer = ActorMaterializer()
  implicit val wsClient = AhcWSClient()(ActorMaterializer()(system))
  implicit val executionContext = system.dispatcher
}
