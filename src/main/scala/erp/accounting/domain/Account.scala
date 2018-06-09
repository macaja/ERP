package erp.accounting.domain

import erp.accounting.domain.errors.{AccountNameIsEmpty, DomainError, TypeAccountNotSupported}
import erp.infrastructure.http.AccountDTO

sealed trait AccountType

case object Active extends AccountType
case object Passive extends AccountType
case object Capital extends AccountType
case object Income extends AccountType
case object Expenses extends AccountType
case object CostsOfSale extends AccountType
case object ProductionCosts extends AccountType

object AccountType {
  def apply(accountType: String): Either[DomainError,AccountType] = accountType.toLowerCase match {
    case "active" => Right(Active)
    case "passive" => Right(Passive)
    case "capital" => Right(Capital)
    case "income" => Right(Income)
    case "expenses" => Right(Expenses)
    case "costsofsale" => Right(CostsOfSale)
    case "productioncosts" => Right(ProductionCosts)
    case _ => Left(TypeAccountNotSupported(accountType))
  }
}

case class Account(id: Option[Int],name: String,accountType: AccountType,duty: List[Deposit],having: List[Deposit], balance: Double)

object Account{
  def apply(accountDTO: AccountDTO): Either[DomainError, Account] = {
    for{
      n <- validateName(accountDTO.name)
      ta <- AccountType(accountDTO.typeAccount)
    } yield new Account(None,n,ta, Nil, Nil, 0D)
  }

  private[this] def validateName(name:String): Either[DomainError, String] =
    if(name.isEmpty) Left(AccountNameIsEmpty()) else Right(name)
}