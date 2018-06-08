package erp.accounting

import erp.accounting.domain.errors.{DomainError, WrongAmountValue}

package object services {

  def validateAmount(amount: Double): Either[DomainError, Double] =
    if(amount <= 0D) Left(WrongAmountValue()) else Right(amount)

}
