package erp.accounting.services

import cats.data.EitherT
import cats.instances.future._
import erp.accounting.ERPResources
import erp.accounting.domain.Account
import erp.accounting.domain.errors.DomainError
import erp.infrastructure.http.{AccountDTO, AccountResponseDTO}

import scala.concurrent.{ExecutionContext, Future}

case class CreateAccount(dto: AccountDTO)(implicit resources: ERPResources, ec: ExecutionContext) {

  def execute: EitherT[Future, DomainError, AccountResponseDTO] = for{
    a <- EitherT.fromEither[Future](Account(dto))
    r <- EitherT.right[DomainError](resources.repository.storeAccount(transformAccountIntoAccountRecord(a)))
  }yield transformAccountRecordToAccountResponseDTO(r,a.accountType,Nil,Nil,a.balance)

}
