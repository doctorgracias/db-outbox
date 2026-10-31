package service

import dao.PaymentDAO
import model.{Payment, PaymentOutBox}
import zio._

case class PaymentService(paymentDAO: PaymentDAO) {

  def process(payment : Payment): Task[Unit] = {
      paymentDAO.sendPayment(payment)
  }

}

object PaymentService {
  val layer = ZLayer.fromFunction(PaymentService(_))
}
