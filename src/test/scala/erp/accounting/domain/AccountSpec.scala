package erp.accounting.domain

import org.scalatest.{AsyncWordSpec, Matchers}

class AccountSpec extends AsyncWordSpec with Matchers{

  "Account domain" should {
    "identify type account" in {
      Type("active").isRight should be(true)
    }
  }

}
