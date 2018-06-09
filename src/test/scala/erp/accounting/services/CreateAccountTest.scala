package erp.accounting.services

import erp.accounting.ERPResourcesMock
import erp.infrastructure.http.AccountDTO
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{AsyncWordSpec, Matchers}

class CreateAccountTest extends AsyncWordSpec with ScalaFutures with  Matchers{
  import CreateAccountTest._
  "Create Account service" should{
    implicit val resources = ERPResourcesMock
    "create an account successfully" in{
      val create = CreateAccount(dto)(resources,scala.concurrent.ExecutionContext.Implicits.global).execute.value
      whenReady(create){ r =>
        r.isRight should be(true)
      }
    }
    "return a service error if account name is empty" in{
      val create = CreateAccount(dto.copy(name = ""))(resources, scala.concurrent.ExecutionContext.Implicits.global).execute.value
      whenReady(create){ r =>
        r.isLeft should be(true)
      }
    }
  }
}
object CreateAccountTest {
  val dto = AccountDTO(
    id = None,
    name = "name",
    typeAccount = "active"
  )
}
