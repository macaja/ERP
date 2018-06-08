package erp.accounting.services

import erp.accounting.domain.Account
import erp.accounting.domain.errors.DomainError

class DebitAccount(account: Account, amount: Double) extends BalanceCalculator {

  def execute: Either[DomainError, Account] ={
    validateAmount(amount).map(a => {
      val dutyUpdated = account.duty :+ a
      account.copy(duty = dutyUpdated,balance = calculateBalance(account.typeAccount,dutyUpdated,account.having))
    })
  }
}
