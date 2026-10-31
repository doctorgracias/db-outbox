package config

import slick.jdbc.PostgresProfile.api._
import zio.{ZIO, ZLayer}

object Connection {
  val db: Database = Database.forConfig("payments")

  val dbLayer = ZLayer.scoped(
    ZIO.acquireRelease(ZIO.attempt(Database.forConfig("payments")))
    (db => ZIO.succeed(db.close()))
  )
}
