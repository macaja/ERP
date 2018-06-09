package erp.infrastructure.mysql

sealed trait Record

final case class AccountRecord(id: Int,name: String, accountType: String) extends Record
final case class DebitDepositRecord(id: Int,accountId: Int,amount: Double, date: String) extends Record
final case class CreditDepositRecord(id: Int,accountId: Int, amount: Double, date: String) extends Record

final case class DepositRecord(value: Double, date: String) extends Record