package erp.accounting.domain

import erp.accounting.domain.errors.{AccountNameIsEmpty, DomainError}

case class Account(name: String)

object Account{
  def apply(name: String): Either[DomainError, Account] = {
    if(name.isEmpty) Left(AccountNameIsEmpty()) else Right(new Account(name))
  }
}