package Calendar

import CommonStuff.DB
import akka.http.scaladsl.server.{Directives, Route}
import com.datastax.driver.core.{Cluster, Row, Session}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
object CalendarController extends Directives with CalendarJsonSupport {

  implicit val DBConnect = DB.Connect;

  def CalendarMapper (DB: Session) : List[CalendarDate] = {
    val QueryRS = DB.execute("SELECT * from starterapp.Calendar WHERE dayname='Monday' LIMIT 10 ALLOW FILTERING" )
    val QueryList = QueryRS.all().toArray.toList.map(row => {
      val r = row.asInstanceOf[Row]
      CalendarDate(
        r.getString("datenum"),
        r.getString("calendar_quarter") ,
        r.getString("date"),
        r.getString("dayname"),
        r.getInt("daynumofmonth"),
        r.getInt("daynumofquarter"),
        r.getInt("daynumofweek"),
        r.getInt("daynumofyear"),
        r.getString("dayshortname"),
        r.getString("monthname"),
        r.getInt("monthnum"),
        r.getString("monthshortname"),
        r.getInt("quarter"),
        r.getInt("weeknum"),
        r.getString("yearmonthnum"),
        r.getString("yearquarternum")
      )
    })
    QueryList
    }
  val CalendarRoute : Route =
    cors() {
      path("calendar") {
        get {
          complete(CalendarMapper(DBConnect))
        }
      }
    }
}