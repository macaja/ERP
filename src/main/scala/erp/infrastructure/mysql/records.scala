package erp.infrastructure.mysql

sealed trait Record

final case class AccountRecord(id: Int,name: String) extends Record