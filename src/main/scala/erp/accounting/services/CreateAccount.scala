package erp.accounting.services

import cats.data.EitherT
import erp.accounting.ERPResources
import erp.accounting.domain.Account
import erp.accounting.domain.errors.DomainError
import erp.infrastructure.http.AccountDTO
import erp.infrastructure.mysql.AccountRecord
import cats.instances.future._

import scala.concurrent.{ExecutionContext, Future}

case class CreateAccount(dto: AccountDTO)(implicit resources: ERPResources, ec: ExecutionContext) {

  def execute: EitherT[Future, DomainError, Account] = for{
    a <- EitherT.fromEither[Future](Account(dto))
    _ <- EitherT.right[DomainError](resources.repository.storeAccount(transformAccountIntoAccountRecord(a)))
  }yield a

  private def transformAccountIntoAccountRecord: Account => AccountRecord = account => {
    AccountRecord(
      id = Math.random().toInt,
      name = account.name
    )
  }

}
