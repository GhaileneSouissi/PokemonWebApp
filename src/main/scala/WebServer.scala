import akka.http.scaladsl.Http
import utils.{Implicits, Routes}

import scala.io.StdIn


object WebServer extends App
  with Implicits
  with Routes {




  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  StdIn.readLine() // let it run until user presses return
  bindingFuture
    .flatMap(_.unbind()) // trigger unbinding from the port
    .onComplete(_ => system.terminate()) // and shutdown when done


}


