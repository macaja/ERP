package erp.infrastructure.http

import erp.accounting.ERPResources
import erp.infrastructure.http.api.Commands

import scala.concurrent.ExecutionContext

trait Routes extends Commands {

  implicit val ec: ExecutionContext
  implicit val erpResources: ERPResources

  def routes = createAccountingAccount

}
