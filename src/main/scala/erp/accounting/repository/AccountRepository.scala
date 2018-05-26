package erp.accounting.repository

import erp.Repository
import erp.accounting.domain.Account
import erp.infrastructure.mysql.daos.AccountDAO
import slick.jdbc.MySQLProfile

class AccountRepository(database: MySQLProfile.backend.Database) extends Repository{

  def storeNewAccount(account: Account) = database.run(AccountDAO.store(account))

}
