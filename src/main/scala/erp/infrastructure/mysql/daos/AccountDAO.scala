package erp.infrastructure.mysql.daos

import erp.infrastructure.mysql.AccountRecord
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class Accounts(tag: Tag) extends Table[AccountRecord](tag, "ACCOUNT") {
  def id = column[Int]("ID", O.PrimaryKey)
  def name = column[String]("NAME")
  def * = (id, name) <> (AccountRecord.tupled, AccountRecord.unapply)

 // def accounts = TableQuery[AccountRecord]
}

abstract class AccountsDAO(db: MySQLProfile.backend.Database)(implicit ec: ExecutionContext) extends TableQuery(new Accounts(_)) {
  def findById(id: Int): Future[Option[AccountRecord]] = {
    db.run(this.filter(_.id === id).result).map(_.headOption)
  }

  def store(account: AccountRecord): Future[AccountRecord] = {
    db.run(this returning this.map(_.id) into ((acc, id) => acc.copy(id = id)) += account)
  }

  def deleteById(id: Int): Future[Int] = {
    db.run(this.filter(_.id === id).delete)
  }
}
