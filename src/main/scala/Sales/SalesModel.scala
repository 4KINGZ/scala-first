package Sales

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class SalesModel (
                        datenum : String ="",
                        sku : String ="",
                        str : String ="",

                        sales : Int = 0,
                        itemLabel : Option[String] = None
                      )
case class Sales (items : List[SalesModel])

trait SalesJsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val saleFormat = jsonFormat5(SalesModel)
  implicit val salesFormat = jsonFormat1(Sales)
}