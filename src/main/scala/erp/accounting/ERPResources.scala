package erp.accounting

import erp.accounting.repositories.ERPRepository

trait ERPResources {
  def repository: ERPRepository
}
