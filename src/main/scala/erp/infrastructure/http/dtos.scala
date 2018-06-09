package erp.infrastructure.http

sealed trait DTO

final case class AccountDTO(id:Option[Int], name: String, typeAccount: String) extends DTO

final case class DepositDTO(amount: Double) extends DTO

final case class AccountResponseDTO(id: Int,name: String, accountType: String,balance: Double, duties: List[DepositResponseDTO], havings: List[DepositResponseDTO])

final case class DepositResponseDTO(amount: Double, date: String)
