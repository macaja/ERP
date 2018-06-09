package erp.accounting.services

import cats.data.EitherT
import cats.instances.future._
import erp.accounting.ERPResources
import erp.accounting.domain.errors.{AccountDoesNotExists, DomainError}
import erp.accounting.domain.{AccountType, Deposit}
import erp.infrastructure.http.{AccountResponseDTO, DepositDTO}
import erp.infrastructure.mysql.CreditDepositRecord

import scala.concurrent.{ExecutionContext, Future}

case class CreditAccount(accountNumber: Int, depositDto: DepositDTO)(implicit resources: ERPResources, ec: ExecutionContext) extends BalanceCalculator {

  def execute: EitherT[Future, DomainError, AccountResponseDTO] ={
    for{
      r <- EitherT(resources.repository.findAccountById(accountNumber).map(_.toRight(AccountDoesNotExists(accountNumber))))
      at <- EitherT.fromEither[Future](AccountType(r.accountType))
      d <- EitherT.fromEither[Future](Deposit(depositDto))
      credit = CreditDepositRecord(0,r.id,d.amount,d.date.toString)
      _ <- EitherT.right[DomainError](resources.repository.saveCredit(credit))
      credits <- EitherT.right[DomainError](resources.repository.getCreditsByAccountId(r.id).map(_.toList))
      debits <- EitherT.right[DomainError](resources.repository.getDebitsByAccountId(r.id).map(_.toList))
      balance = calculateBalance(at,debits.map(transformDepositRecordToDeposit(_)),credits.map(transformDepositRecordToDeposit(_)))
    }yield transformAccountRecordToAccountResponseDTO(r,at,debits,credits,balance)
  }

}
