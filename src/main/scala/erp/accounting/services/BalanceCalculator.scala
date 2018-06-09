package erp.accounting.services

import erp.accounting.domain._

trait BalanceCalculator {
  def calculateBalance(typeAccount: AccountType,duty: List[Deposit], having: List[Deposit]): Double = typeAccount match{
    case Active | Income | CostsOfSale | ProductionCosts => duty.map(_.amount).sum - having.map(_.amount).sum
    case _ => having.map(_.amount).sum - duty.map(_.amount).sum
  }
}
