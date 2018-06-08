package erp.accounting.domain

object errors {

  sealed trait DomainError{
    def message: String
  }

  final case class AccountNameIsEmpty(message: String = "Account name is empty") extends DomainError
  final case class TypeAccountNotSupported(message: String) extends DomainError
  final case class WrongAmountValue(message: String = "Wrong amount value") extends DomainError

  object TypeAccountNotSupported{
    def apply(invalidType: String): TypeAccountNotSupported = new TypeAccountNotSupported(s"Account type $invalidType is not supported")
  }

}
