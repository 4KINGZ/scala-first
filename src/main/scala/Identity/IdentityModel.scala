package Identity
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class User(
               username : String = "",
               password : String = "",
               salt : Option[String] = None,
               user_id: Option[String] = None
                )

trait UserJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val userFormat = jsonFormat4(User)
}

case class Users (items : List [User])



