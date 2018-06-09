package erp.infrastructure.mysql

import erp.infrastructure.mysql.daos.{Accounts, CreditDeposits, DebitDeposits}
import slick.jdbc.MySQLProfile.api._
// $COVERAGE-OFF$
object tables {

  def accounts = TableQuery[Accounts]

  def debitDeposits = TableQuery[DebitDeposits]

  def creditDeposits = TableQuery[CreditDeposits]

  val setup = DBIO.sequence(
    List(
      accounts,
      debitDeposits,
      creditDeposits
    ).map(_.schema.create)
  )
  // $COVERAGE-ON$
}

