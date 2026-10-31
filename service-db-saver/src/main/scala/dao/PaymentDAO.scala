package dao

import model.{Payment, PaymentOutBox, PaymentTable, PaymentTableOutBox}
import slick.jdbc.PostgresProfile.api._
import zio._

import java.time.Instant
import scala.language.postfixOps

class PaymentDAO(db: Database) {

  def sendPayment(payment: Payment): Task[Unit] = {
    ZIO.fromFuture { implicit ex =>
      db.run(
        (for {
          insertedId <- (paymentTable returning paymentTable.map(_.id)) += payment
          _          <- paymentTableOutBox += getOutBox(payment, insertedId.get)
        } yield ()).transactionally
      )
    }
  }

  private def getOutBox(payment : Payment, id : Long) = {
    PaymentOutBox(
      id = None,
      payment_id = id,
      amount = payment.amount,
      sender = payment.sender,
      receiver = payment.receiver,
      created_at = payment.created_at,
      sent_at = None
    )
  }

  private val paymentTable = TableQuery[PaymentTable]
  private val paymentTableOutBox = TableQuery[PaymentTableOutBox]

}

object PaymentDAO {
  val layer: ZLayer[Database, Nothing, PaymentDAO] =
    ZLayer.fromFunction(new PaymentDAO(_))
}