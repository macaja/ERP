package erp.accounting.domain

object errors {

  sealed trait DomainError{
    def message: String
  }

  final case class AccountNameIsEmpty(message: String = "Account name is empty") extends DomainError

}
