package ERP.domain

//Haber
case class Having(valor: List[Double])

object Having {
  def apply(valor: List[Double]): Having = new Having(valor)

}