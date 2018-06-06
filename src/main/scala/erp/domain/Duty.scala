package ERP.domain

//Deber
case class Duty(duties: List[Double])

object Duty {
  def apply(duties: List[Double]): Duty = new Duty(duties)
}

