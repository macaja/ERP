package erp

import erp.accounting.repository.AccountRepository

trait ErpEnvironment {
  def repository: AccountRepository
}
