package Calendar

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class CalendarDate(
                               datenum : String = "",
                               calendar_quarter : String = "" ,
                               date : String = "",
                               dayname : String = "",
                               daynumofmonth : Int = 0,
                               daynumofquarter : Int = 0,
                               daynumofwwek : Int = 0,
                               daynumofyear : Int = 0,
                               dayshortname : String = "",
                               monthname : String = "",
                               monthnum : Int = 0,
                               monthshortname : String = "",
                               quarter : Int = 0,
                               weeknum : Int = 0,
                               yearmonthnum : String = "",
                               yearquarternum : String = ""
                             )
trait CalendarJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val dateFormat = jsonFormat16(CalendarDate)
  implicit val calFormat = jsonFormat1(Calendar)
}


case class Calendar(items: List[CalendarDate])