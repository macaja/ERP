package erp.accounting

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

import erp.accounting.domain.{Account, AccountType, Deposit}
import erp.accounting.domain.errors.{DomainError, WrongAmountValue}
import erp.infrastructure.http.{AccountResponseDTO, DepositResponseDTO}
import erp.infrastructure.mysql.{AccountRecord, DepositRecord}

package object services {

  def validateAmount(amount: Double): Either[DomainError, Double] =
    if(amount <= 0D) Left(WrongAmountValue()) else Right(amount)

  def transformAccountIntoAccountRecord: Account => AccountRecord = account => {
    AccountRecord(
      id = account.id.fold(0)(identity),
      name = account.name,
      accountType = account.accountType.toString
    )
  }

  def transformAccountRecordToAccount(record: AccountRecord, accountType: AccountType,duties:List[DepositRecord], havings: List[DepositRecord], balance: Double): Account =
    Account(
      id = Some(record.id),
      name = record.name,
      accountType = accountType,
      duty = duties.map(transformDepositRecordToDeposit(_)),
      having = havings.map(transformDepositRecordToDeposit(_)),
      balance = balance
    )

  def transformDepositRecordToDeposit: DepositRecord => Deposit = record => {
    new Deposit(
      amount = record.value,
      date = ZonedDateTime.parse(record.date)
    )
  }

  def transformAccountRecordToAccountResponseDTO(record: AccountRecord, accountType: AccountType,duties:List[DepositRecord], havings: List[DepositRecord], balance: Double): AccountResponseDTO =
    AccountResponseDTO(
      id = record.id,
      name = record.name,
      balance = balance,
      accountType = accountType.toString,
      duties = duties.map(transformDepositRecordToDepositResponseDTO(_)),
      havings = havings.map(transformDepositRecordToDepositResponseDTO(_))
    )

  def transformDepositRecordToDepositResponseDTO: DepositRecord => DepositResponseDTO = record => {
    DepositResponseDTO(
      amount = record.value,
      date = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss").format(ZonedDateTime.parse(record.date))
    )
  }

}
