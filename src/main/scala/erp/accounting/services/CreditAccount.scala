package erp.accounting.services

import erp.accounting.domain.Account
import erp.accounting.domain.errors.DomainError

class CreditAccount(account: Account, amount: Double) extends BalanceCalculator {

  def execute: Either[DomainError, Account] ={
    validateAmount(amount).map(a => {
      val havingUpdated = account.having :+ a
      account.copy(having = havingUpdated,balance = calculateBalance(account.typeAccount,account.duty,havingUpdated))
    })
  }

}
