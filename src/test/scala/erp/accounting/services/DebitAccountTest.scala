package erp.accounting.services

import erp.accounting.ERPResourcesMock
import erp.infrastructure.http.DepositDTO
import org.scalatest.{AsyncWordSpec, Matchers}
import org.scalatest.concurrent.ScalaFutures

class DebitAccountTest extends AsyncWordSpec with ScalaFutures with Matchers{
  import DebitAccountTest._
  "Debit Account service" should{
    implicit val resources = ERPResourcesMock
    "make a debit successfully" in{
      val debit = DebitAccount(2,dto)(resources, scala.concurrent.ExecutionContext.Implicits.global).execute.value
      whenReady(debit){r =>
        r.isRight should be(true)
      }
    }
  }

}
object DebitAccountTest{
  val dto  = DepositDTO(
    amount = 100.0
  )
}