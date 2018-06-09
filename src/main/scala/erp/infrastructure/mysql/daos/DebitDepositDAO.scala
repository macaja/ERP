package erp.infrastructure.mysql.daos

import erp.infrastructure.mysql.{DebitDepositRecord, DepositRecord, tables}
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class DebitDeposits(tag: Tag) extends Table[DebitDepositRecord](tag, "DEBITDEPOSIT"){
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def accountId = column[Int]("ACC_ID")
  def amount = column[Double]("VALUE")
  def date = column[String]("DATE")

  def accounts = foreignKey("ACC_ID",accountId, tables.accounts)(_.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Cascade)

  def * = (id,accountId,amount,date) <> (DebitDepositRecord.tupled, DebitDepositRecord.unapply)
}

abstract class DebitDepositsDAO(db: MySQLProfile.backend.Database)(implicit ec: ExecutionContext) extends TableQuery(new DebitDeposits(_)){
  def store(debitDeposit: DebitDepositRecord) =
    db.run(this returning this.map(_.id) into ((acc,id) => acc.copy(id = id)) += debitDeposit)

  def findById(id: Int): Future[Option[DebitDepositRecord]] =
    db.run(this.filter(_.id === id).result).map(_.headOption)

  def deleteById(id: Int): Future[Int] =
    db.run(this.filter(_.id === id).delete)

  def getDebitsByAccountId(accountId: Int): Future[Vector[DepositRecord]] = {
    val action = sql"select value,date from DEBITDEPOSIT C join ACCOUNT A where C.ACC_ID=$accountId and A.ID=$accountId".as[(Double, String)]
    db.run(action.map(_.map(r => DepositRecord(r._1,r._2))))
  }

}
