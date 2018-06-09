package erp.accounting.domain

import erp.accounting.domain.errors.{AccountNameIsEmpty, TypeAccountNotSupported}
import erp.infrastructure.http.AccountDTO
import org.scalatest.{AsyncWordSpec, Matchers}

class AccountSpec extends AsyncWordSpec with Matchers{

  "Account domain" should {
    "identify account type successfully" in {
      val validAccountTypes: List[Either[errors.DomainError, AccountType]] = List(
        "Active",
        "Passive",
        "Capital",
        "Income",
        "Expenses",
        "CostsOfSale",
        "ProductionCosts"
      ).map(AccountType(_))
      validAccountTypes.forall(_.isRight) should be(true)
    }
    "return a domain error if account type is not supported" in{
      val invalidAccountType = AccountType("x")
      invalidAccountType.isLeft should be(true)
      invalidAccountType.fold(_ shouldBe TypeAccountNotSupported("x"),
        _ => fail
      )
    }

    "create an account successfully from accountDTO" in{
      val dto = AccountDTO(None,"name","active")
      Account(dto).fold(_ => succeed,
        _ shouldBe Account(
          None,
          "name",
          Active,
          Nil,
          Nil,
          0
        )
      )
    }

    "return a domain error if account name is empty" in{
      val dto = AccountDTO(None,"","active")
      Account(dto).fold(_ shouldBe AccountNameIsEmpty(),_ => fail)
    }
  }

}
