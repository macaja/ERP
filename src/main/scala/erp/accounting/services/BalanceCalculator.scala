package erp.accounting.services

import erp.accounting.domain._

trait BalanceCalculator {
  def calculateBalance(typeAccount: TypeAccount,duty: List[Double], having: List[Double]): Double = typeAccount match{
    case Active | Income | CostsOfSale | ProductionCosts => duty.sum - having.sum
    case _ => having.sum - duty.sum
  }
}
