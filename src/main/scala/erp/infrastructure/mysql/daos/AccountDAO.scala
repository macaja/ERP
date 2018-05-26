package erp.infrastructure.mysql.daos

import erp.infrastructure.mysql.AccountRecord
import slick.jdbc.MySQLProfile.api._

class Accounts(tag: Tag) extends Table[AccountRecord](tag, "ACCOUNT") {
  def id = column[Int]("ID", O.PrimaryKey)
  def name = column[String]("NAME")
  def * = (id, name) <> (AccountRecord.tupled, AccountRecord.unapply)

  def Companies = TableQuery[AccountRecord]
}

object AccountDAO extends TableQuery(new Accounts(_)){
  def store(account: AccountRecord) = AccountDAO += account
  def getAccountById(id: Int) = AccountDAO.filter(_.id === id).result
}
