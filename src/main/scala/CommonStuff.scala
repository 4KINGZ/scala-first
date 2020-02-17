package CommonStuff

import Calendar._
import Identity._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.datastax.driver.core.{Cluster, Session}

import akka.http.scaladsl.server.Directives._
import scala.io.StdIn

object ServerConfig {
  val http_interface = "localhost"
  val http_port = 8080
}
object DB {
  implicit val Connect = new Cluster
  .Builder()
    .addContactPoints("localhost")
    .withPort(9042)
    .build()
    .connect()
}
object MainRouter {

  val Routes = CalendarController.CalendarRoute ~ IdentityController.LoginRoute
}

object Server{

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher


  def main (args :Array[String]){

    val bindingFuture = Http().bindAndHandle(MainRouter.Routes,
      ServerConfig.http_interface,
      ServerConfig.http_port)

    println(s"Server online at http://{}:{}/\nPress RETURN to stop...",(ServerConfig.http_interface, ServerConfig.http_port))
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }

}





