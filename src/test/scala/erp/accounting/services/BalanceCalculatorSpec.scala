package erp.accounting.services

import erp.accounting.domain.Passive
import org.scalatest.{AsyncWordSpec, Matchers}

class BalanceCalculatorSpec extends AsyncWordSpec with Matchers with BalanceCalculator {

  "Balance Calculator" should{
    "balance credit" in{
      val c: Double = calculateBalance(Passive,Nil,Nil)
      c should be(0)
    }
  }

}
