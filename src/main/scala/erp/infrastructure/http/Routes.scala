package erp.infrastructure.http

import erp.infrastructure.http.api.Commands

trait Routes extends Commands {

  def routes = createAccountingAccount

}
