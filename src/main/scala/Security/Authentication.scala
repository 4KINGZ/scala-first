package Security


import java.net.Authenticator

import Identity.User
import akka.http.scaladsl.server.directives.{AuthenticationDirective, Credentials}
import akka.http.scaladsl.server.{AuthenticationFailedRejection, Directive1, Route}
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.directives.Credentials.{Missing, Provided}
import org.mindrot.jbcrypt.BCrypt
import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.Future



final case class LoginRequest(username: String, password: String)

object Authentication {

  def hashPw (pw : String) : List[String] = {
    val salt = BCrypt.gensalt()

    List(BCrypt.hashpw(pw,salt ),salt)

  }
  def verifySecret (pw : String , hash : String): Boolean = {
    BCrypt.checkpw(pw, hash)
  }



}
