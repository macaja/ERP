package erp.accounting
import erp.accounting.repositories.ERPRepository
import erp.infrastructure.mysql.{AccountRecord, CreditDepositRecord, DebitDepositRecord, DepositRecord}
import erp.infrastructure.mysql.database.ERPDatabase
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object ERPResourcesMock extends ERPResources {

  implicit val provider: ERPDatabase = new ERPDatabase(Database.forConfig("mysql"))

  val accountRecord = AccountRecord(
    id = 1,
    name = "name",
    accountType = "active"
  )

  val depositRecord = DepositRecord(
    value = 500.0,
    date = "2018-06-09T03:59:10.005-05:00[America/Bogota]"
  )

  override def repository: ERPRepository = new ERPRepository{
    override def storeAccount(accountRecord: AccountRecord): Future[AccountRecord] =
      Future.successful(accountRecord)

    override def findAccountById(id: Int): Future[Option[AccountRecord]] = {
      var calls = 0
      if(calls == 0){
        calls += 1
        Future.successful(Some(accountRecord))
      }else{
        calls += 1
        Future.successful(Some(accountRecord.copy(accountType = "passive")))
      }
    }

    override def saveCredit(credit: CreditDepositRecord): Future[CreditDepositRecord] =
      Future.successful(CreditDepositRecord(id=1,accountId = 1,amount = 100.0,date = "2018-06-09T03:59:10.005-05:00[America/Bogota]"))

    override def saveDebit(debit: DebitDepositRecord): Future[DebitDepositRecord] =
      Future.successful(DebitDepositRecord(id=1,accountId = 1,amount = 500.0,date = "2018-06-09T03:59:10.005-05:00[America/Bogota]"))

    override def getCreditsByAccountId(accountId: Int): Future[Vector[DepositRecord]] =
      Future.successful(
        Vector(
          depositRecord,
          depositRecord
        )
      )

    override def getDebitsByAccountId(accountId: Int): Future[Vector[DepositRecord]] =
      Future.successful(
        Vector(
          depositRecord,
          depositRecord,
          depositRecord
        )
      )
  }

}
