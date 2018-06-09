package erp.accounting.services

import erp.accounting.ERPResourcesMock
import erp.infrastructure.http.DepositDTO
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{AsyncWordSpec, Matchers}

class CreditAccountTest extends AsyncWordSpec with ScalaFutures with Matchers{
  import CreditAccountTest._
  "Credit account service" should{
    implicit val resources = ERPResourcesMock
    "make a credit successfully" in{
      val credit = CreditAccount(1,dto)(resources,scala.concurrent.ExecutionContext.Implicits.global).execute.value
      whenReady(credit){r =>
        r.isRight should be(true)
      }
    }
  }

}
object CreditAccountTest{
  val dto = DepositDTO(
    amount = 500.0
  )
}