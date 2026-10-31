package service

import dao.PaymentDAO
import zio.{Task, ZIO, ZLayer, durationInt}

case class PollingService(paymentDAO: PaymentDAO) {

  val poll: Task[Unit] =
    (for {
      records <- paymentDAO.getRawPayment()
      _       <-           ZIO.when(!records.isEmpty) {
        ZIO.foreach(records) { record =>
          paymentDAO.markSent(record.id.get) *> ZIO.log("Sending an message into topic")
        }
      }
      _       <- ZIO.sleep(5.seconds)
    } yield ()).forever

}

object PollingService {
  val layer = ZLayer.fromFunction(new PollingService(_))
}
