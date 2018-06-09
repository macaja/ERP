package erp

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.{ActorMaterializer, Materializer}
import erp.accounting.ERPResources
import erp.accounting.repositories.ERPRepository
import erp.infrastructure.configuration.ERPConfiguration
import erp.infrastructure.http.Routes
import erp.infrastructure.mysql.database.ERPDatabase
import erp.infrastructure.mysql.tables
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

object Main extends Routes with App {

  implicit val system: ActorSystem = ActorSystem("erp-accounting")
  implicit val materializer: Materializer = ActorMaterializer()
  implicit val ec: ExecutionContext = system.dispatcher

  val config = ERPConfiguration

  val db: MySQLProfile.backend.Database = Database.forConfig("mysql")

  db.run(tables.setup)

  implicit val provider: ERPDatabase = new ERPDatabase(db)

  implicit val erpResources: ERPResources = new ERPResources {
    override def repository: ERPRepository = new ERPRepository
  }

  Http().bindAndHandle(routes,config.httpConfig.host,config.httpConfig.port) onComplete{
    case Failure(ex) => println(s"Failed to bind http service due to $ex")
    case Success(_) => println(s"Listening in port 8080...")
  }

}
