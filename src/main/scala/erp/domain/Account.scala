package ERP.domain

import ERP.DomainServiceError

case class Account (name: String, duty: Duty, having: Having)

object Account {
  def apply (name: String) : Either[DomainServiceError, Account] = {
    for {
      d <- Duty(Nil)
      h <- Having(Nil)
    } yield new Account(name, d, h)
  }
}
