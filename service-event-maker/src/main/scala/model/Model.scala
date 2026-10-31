package model

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

import java.time.Instant
import io.circe._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}


case class Payment(
                   id: Option[Long] = None,
                   amount : Int,
                   sender : String,
                   receiver : String,
                   created_at : Instant
                 )

class PaymentTable(tag : Tag) extends Table[Payment](tag,"payment"){
  def id = column[Option[Long]]("id",O.PrimaryKey,O.AutoInc)
  def amount = column[Int]("amount")
  def sender = column[String]("sender")
  def receiver = column[String]("receiver")
  def created_at = column[Instant]("created_at")
  override def * : ProvenShape[Payment] =
  (id,amount,sender,receiver,created_at)<>(Payment.tupled,Payment.unapply)
}

case class PaymentOutBox(
                    id: Option[Long] = None,
                    payment_id : Long,
                    amount : Int,
                    sender : String,
                    receiver : String,
                    created_at : Instant,
                    sent_at : Option[Instant]
                  )

class PaymentTableOutBox(tag : Tag) extends Table[PaymentOutBox](tag,"payment_outbox"){
  def id = column[Option[Long]]("id",O.PrimaryKey,O.AutoInc)
  def payment_id = column[Long]("payment_id")
  def amount = column[Int]("amount")
  def sender = column[String]("sender")
  def receiver = column[String]("receiver")
  def created_at = column[Instant]("created_at")
  def sent_at = column[Option[Instant]]("sent_at")
  override def * : ProvenShape[PaymentOutBox] =
    (id,payment_id, amount,sender,receiver,created_at, sent_at)<>(PaymentOutBox.tupled,PaymentOutBox.unapply)
}

object Payment {
  implicit val decoder : Decoder[Payment] = deriveDecoder
  implicit val encoder : Encoder[Payment] = deriveEncoder
}
