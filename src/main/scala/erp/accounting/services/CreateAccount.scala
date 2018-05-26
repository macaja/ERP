package erp.accounting.services

import erp.accounting.domain.Account
import erp.accounting.domain.errors.DomainError
import erp.infrastructure.http.AccountDTO

case class CreateAccount(dto: AccountDTO) {

  def execute(implicit env: ErpEnvironment): Either[DomainError, Account] = for{
    a <- Account(dto.name)
  }yield a

}
