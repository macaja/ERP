package erp.infrastructure.http.api

import akka.http.scaladsl.server.Directives._
import erp.infrastructure.http.{AccountDTO, DepositDTO}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import erp.accounting.ERPResources
import io.circe.generic.auto._
import io.circe.syntax._
import erp.accounting.services.{CreateAccount, CreditAccount, DebitAccount}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

trait Commands {

  implicit val ec: ExecutionContext
  implicit val erpResources: ERPResources

  def createAccountingAccount: Route =
    path("account"){
      post{
        entity(as[AccountDTO]){ account =>
          onComplete(CreateAccount(account).execute.value){
            case Failure(ex) => complete(BadRequest -> s"Request failed with exception $ex")
            case Success(v) => v.fold(
              error => complete(BadRequest -> s"$error"),
              account => complete(OK -> account.asJson)
            )
          }
        }
      }
    }

  def creditAccount: Route =
    path("account"/"credit"/ IntNumber){ accountNumber =>
      post{
        entity(as[DepositDTO]){ deposit =>
          onComplete(CreditAccount(accountNumber,deposit).execute.value){
            case Failure(ex) => complete(BadRequest -> s"Request failed with exception $ex")
            case Success(v) => v.fold(
              error => complete(BadRequest -> s"$error"),
              account => complete(OK -> account.asJson)
            )
          }
        }
      }
    }

  def debitAccount: Route =
    path("account"/"debit"/ IntNumber){ accountNumber =>
      post{
        entity(as[DepositDTO]){ deposit =>
          onComplete(DebitAccount(accountNumber,deposit).execute.value){
            case Failure(ex) => complete(BadRequest -> s"Request failed with exception $ex")
            case Success(v) => v.fold(
              error => complete(BadRequest -> s"$error"),
              account => complete(OK -> account.asJson)
            )
          }
        }
      }
    }

  val accountingERPRoutes = createAccountingAccount ~ creditAccount ~ debitAccount

}
