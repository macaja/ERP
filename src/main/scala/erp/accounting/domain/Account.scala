package erp.accounting.domain

import java.util.UUID

import erp.accounting.domain.errors.{AccountNameIsEmpty, DomainError, TypeAccountNotSupported}
import erp.infrastructure.http.AccountDTO

sealed trait TypeAccount

case object Active extends TypeAccount
case object Passive extends TypeAccount
case object Capital extends TypeAccount
case object Income extends TypeAccount
case object Expenses extends TypeAccount
case object CostsOfSale extends TypeAccount
case object ProductionCosts extends TypeAccount

object Type {
  def apply(typeAccount: String): Either[DomainError,TypeAccount] = typeAccount.toLowerCase match {
    case "active" => Right(Active)
    case "passive" => Right(Passive)
    case "capital" => Right(Capital)
    case "income" => Right(Income)
    case "expenses" => Right(Expenses)
    case "costsofsale" => Right(CostsOfSale)
    case "productioncosts" => Right(ProductionCosts)
    case _ => Left(TypeAccountNotSupported(typeAccount))
  }
}

case class Account(code: UUID,name: String,typeAccount: TypeAccount,duty: List[Double],having: List[Double], balance: Double)

object Account{
  def apply(accountDTO: AccountDTO): Either[DomainError, Account] = {
    for{
      n <- validateName(accountDTO.name)
      ta <- Type(accountDTO.typeAccount)
    } yield new Account(UUID.randomUUID(),n,ta, Nil, Nil, 0D)
  }

  private[this] def validateName(name:String): Either[DomainError, String] =
    if(name.isEmpty) Left(AccountNameIsEmpty()) else Right(name)
}