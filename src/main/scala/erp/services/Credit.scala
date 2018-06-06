package ERP.services

import ERP.DomainServiceError
import ERP.domain.{Account, Having}
import ERP.domainErrors.WrongCreditValue

class Credit (account: Account, amount: Double){
  def execute: Either[DomainServiceError, Account] = {
    if(amount <= 0){
      Left(WrongCreditValue())
    }
    for {
      h <-Having(amount :: account.having.valor)
    } yield
    Right(account.copy(having = h))
  }
}
