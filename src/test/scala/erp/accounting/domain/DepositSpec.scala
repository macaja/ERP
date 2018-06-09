package erp.accounting.domain

import erp.accounting.domain.errors.{DepositAmountLessThanZero}
import erp.infrastructure.http.{ DepositDTO}
import org.scalatest.{AsyncWordSpec, Matchers}

class DepositSpec extends AsyncWordSpec with Matchers{

  "create an Deposit succeed" in {
    val depositDTO = DepositDTO(200D)
    Deposit(depositDTO).fold(_ => succeed,
      _.amount shouldBe 200D)
  }

  "return a domain error if amount is 0" in {
    val depositDTO = DepositDTO(0D)
    Deposit(depositDTO).fold(_ shouldBe DepositAmountLessThanZero(),_ => fail)
  }

}
