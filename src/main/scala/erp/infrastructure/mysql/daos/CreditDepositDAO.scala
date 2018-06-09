package erp.infrastructure.mysql.daos

import erp.infrastructure.mysql.{CreditDepositRecord, DepositRecord, tables}
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class CreditDeposits(tag: Tag) extends Table[CreditDepositRecord](tag, "CREDITDEPOSIT"){
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def accountId = column[Int]("ACC_ID")
  def amount = column[Double]("VALUE")
  def date = column[String]("DATE")

  def accounts = foreignKey("ACC_ID",accountId, tables.accounts)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

  def * = (id,accountId,amount,date) <> (CreditDepositRecord.tupled, CreditDepositRecord.unapply)
}

abstract class CreditDepositsDAO(db: MySQLProfile.backend.Database)(implicit ec: ExecutionContext) extends TableQuery(new CreditDeposits(_)){
  def store(creditDeposit: CreditDepositRecord) =
    db.run(this returning this.map(_.id) into ((acc, id) => acc.copy(id = id)) += creditDeposit)

  def findById(id: Int): Future[Option[CreditDepositRecord]] =
    db.run(this.filter(_.id === id).result).map(_.headOption)

  def deleteById(id: Int): Future[Int] =
    db.run(this.filter(_.id === id).delete)

  def getCreditsByAccountId(accountId: Int): Future[Vector[DepositRecord]] = {
    val action = sql"select value,date from CREDITDEPOSIT C join ACCOUNT A where C.ACC_ID=$accountId and A.ID=$accountId".as[(Double, String)]
    db.run(action.map(_.map(r => DepositRecord(r._1,r._2))))
  }

}
