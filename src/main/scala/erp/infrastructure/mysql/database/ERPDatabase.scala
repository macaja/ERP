package erp.infrastructure.mysql.database

import erp.infrastructure.mysql.daos.AccountsDAO
import slick.jdbc.MySQLProfile

import scala.concurrent.ExecutionContext

class ERPDatabase(database: MySQLProfile.backend.Database)(implicit ec: ExecutionContext) {

  object accounts extends AccountsDAO(database)

}
