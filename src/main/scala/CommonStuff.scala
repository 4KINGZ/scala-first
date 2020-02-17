package CommonStuff

import Calendar._
import Identity._
import Sales._
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.datastax.driver.core.{Cluster, Session}

import scala.io.StdIn
import akka.http.scaladsl.model.HttpMethods._
import akka.http.scaladsl.model.headers._
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.{Directive0, Route}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import scala.concurrent.duration._

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

  val Routes = cors() {CalendarController.CalendarRoute ~
                      IdentityController.LoginRoute ~ SalesProvider.SalesRoute}
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





