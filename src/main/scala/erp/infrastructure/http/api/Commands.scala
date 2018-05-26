package erp.infrastructure.http.api

import akka.http.scaladsl.server.Directives._
import erp.infrastructure.http.AccountDTO
import akka.http.scaladsl.model.StatusCodes._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import erp.accounting.services.CreateAccount

trait Commands {

  def createAccountingAccount =
    path("account"){
      post{
        entity(as[AccountDTO]){ account =>
          CreateAccount(account).execute.fold(
            error => complete(BadRequest -> s"$error"),
            account => complete(OK -> s"account created with name ${account.name}")
          )
        }
      }
    }

}
