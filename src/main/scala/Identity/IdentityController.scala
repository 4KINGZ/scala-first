package Identity

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import com.datastax.driver.core.{Cluster, Row, Session}
import java.util.UUID._
import org.mindrot.jbcrypt.BCrypt
import CommonStuff.DB

object IdentityController extends UserJsonSupport {
  implicit val DBConnect = DB.Connect
  // Used to create a user in DB
  def UserWriter (DB : Session, user : User) = {
    val uuid = randomUUID()
    val pwsalt = Security.Authentication.hashPw(user.password)
    val QueryRS = DB.execute("insert into starterapp.users(user_id, username, password, salt) values (?,?,?,?)",
      uuid, user.username, pwsalt(0), pwsalt(1))

}
  def AuthenticateUser (DB : Session, user : User) : Boolean = {
    var DBpw ="" ; var DBsalt =""
    val UserQuery = DB.execute("SELECT * from starterapp.users WHERE username=?",user.username)
    val UserList = UserQuery.all().toArray.toList.map(row => {
      val r = row.asInstanceOf[Row]
      DBpw = r.getString("password")
      DBsalt = r.getString("salt")
    })
    BCrypt.hashpw(user.password, DBsalt)== DBpw
  }
  val LoginRoute =
    path("login") {
      concat(
        get {
          complete("")
        },
        post{
          entity(as[User]){ user =>
            complete{

              if(AuthenticateUser(DBConnect, user)){
                HttpEntity(ContentTypes.`text/html(UTF-8)`, "Password match.")
              }
              else{
                HttpEntity(ContentTypes.`text/html(UTF-8)`, "Error. Wrong username or password, please try again.")
              }
            }
          }
        }
      )

    }

}