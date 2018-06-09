package erp.accounting.repositories

import erp.infrastructure.mysql.AccountRecord
import erp.infrastructure.mysql.database.ERPDatabase

import scala.concurrent.Future

class ERPRepository(implicit val provider: ERPDatabase) {

  def storeAccount(accountRecord: AccountRecord): Future[AccountRecord] =
    provider.accounts.store(accountRecord)

  def findAccountById(id: Int): Future[Option[AccountRecord]] =
    provider.accounts.findById(id)

  def deleteAccountById(id: Int): Future[Int] =
    provider.accounts.deleteById(id)

}
