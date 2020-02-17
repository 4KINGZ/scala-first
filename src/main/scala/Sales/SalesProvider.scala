package Sales
import CommonStuff.DB
import akka.http.scaladsl.server.{Directives, Route}
import com.datastax.driver.core.{Row, Session}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._



object SalesProvider extends Directives with SalesJsonSupport {

  implicit val DBconnect : Session = DB.Connect

  def SalesMapper (DB : Session): List[SalesModel] = {
    val SalesRS = DB.execute("SELECT * from starterapp.sales WHERE sku='hf45' and str = 'S070' LIMIT 10" )
    val skuRS = DB.execute("SELECT * from starterapp.product WHERE sku='hf45' ")
    val itemlabel = skuRS.one().getString("itemlabel")
    println(itemlabel)
    val SalesList = SalesRS.all().toArray.toList.map(row => {

      val r = row.asInstanceOf[Row]
      SalesModel(
        r.getString("datenum"),
        r.getString("sku"),
        r.getString("str"),
        r.getInt("sales"),
        Some(itemlabel)
      )
    })
    SalesList
  }

  val SalesRoute : Route = {
    cors() {
      path("sales") {
        get {
          complete(SalesMapper(DBconnect))
        }
      }
    }
  }



}
