package ERP

object domainErrors {
  final case class WrongCreditValue(
    message: String = "Credit value must be greater than zero")
    extends DomainServiceError

  final case class DebitLisIsEmpty(message: String = "Error creating a debit")
    extends DomainServiceError

  final case class AccountNameIsEmpty(message: String = "Account name is empty")
    extends DomainServiceError


}


