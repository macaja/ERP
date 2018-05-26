package erp.infrastructure.http

sealed trait DTO

case class AccountDTO(name: String) extends DTO
