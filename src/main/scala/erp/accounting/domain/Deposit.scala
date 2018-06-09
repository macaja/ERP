package erp.accounting.domain

import java.time.ZonedDateTime

import erp.accounting.domain.errors.{DepositAmountLessThanZero, DomainError}
import erp.infrastructure.http.DepositDTO

case class Deposit(amount: Double, date: ZonedDateTime)

object Deposit {
  def apply(dto: DepositDTO): Either[DomainError, Deposit] =
    if(dto.amount <= 0) Left(DepositAmountLessThanZero()) else Right(new Deposit(dto.amount,ZonedDateTime.now()))
}
