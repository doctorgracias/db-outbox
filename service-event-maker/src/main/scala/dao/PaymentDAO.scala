package dao

import model.{Payment, PaymentOutBox, PaymentTable, PaymentTableOutBox}
import slick.jdbc.PostgresProfile.api._
import zio._

import java.time.Instant
import scala.language.postfixOps

class PaymentDAO(db: Database) {

  def getRawPayment(): Task[Seq[PaymentOutBox]] = {
    ZIO.fromFuture(implicit ex => db.run(
      paymentTableOutBox
        .filter(_.sent_at.isEmpty)
        .result
    ))
  }

  def markSent(id: Long): Task[Int] = {
    ZIO.fromFuture { implicit ex =>
      db.run(
        paymentTableOutBox
          .filter(_.id === id)
          .map(_.sent_at)
          .update(Some(Instant.now()))
      )
    }
  }

  private val paymentTable = TableQuery[PaymentTable]
  private val paymentTableOutBox = TableQuery[PaymentTableOutBox]

}

object PaymentDAO {
  val layer: ZLayer[Database, Nothing, PaymentDAO] =
    ZLayer.fromFunction(new PaymentDAO(_))
}