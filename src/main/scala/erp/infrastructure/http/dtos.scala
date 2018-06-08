package erp.infrastructure.http

sealed trait DTO

case class AccountDTO(name: String, typeAccount: String) extends DTO
