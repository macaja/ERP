package erp.accounting.repositories

import erp.infrastructure.mysql.database.ERPDatabase
import erp.infrastructure.mysql.{AccountRecord, CreditDepositRecord, DebitDepositRecord, DepositRecord}

import scala.concurrent.Future

class ERPRepository(implicit val provider: ERPDatabase) {

  def storeAccount(accountRecord: AccountRecord): Future[AccountRecord] =
    provider.accounts.store(accountRecord)

  def findAccountById(id: Int): Future[Option[AccountRecord]] =
    provider.accounts.findById(id)

  def deleteAccountById(id: Int): Future[Int] =
    provider.accounts.deleteById(id)

  def saveCredit(credit: CreditDepositRecord): Future[CreditDepositRecord] =
    provider.creditDeposits.store(credit)

  def saveDebit(debit: DebitDepositRecord): Future[DebitDepositRecord] =
    provider.debitDeposits.store(debit)

  def getCreditsByAccountId(accountId: Int): Future[Vector[DepositRecord]] =
    provider.creditDeposits.getCreditsByAccountId(accountId)

  def getDebitsByAccountId(accountId: Int): Future[Vector[DepositRecord]] =
    provider.debitDeposits.getDebitsByAccountId(accountId)
}
