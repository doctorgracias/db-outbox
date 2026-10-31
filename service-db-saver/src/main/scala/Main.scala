import config.Connection
import controller.PaymentController
import dao.PaymentDAO
import service.PaymentService
import slick.jdbc.PostgresProfile.api._
import sttp.tapir.server.netty.zio.NettyZioServer
import sttp.tapir.server.netty.zio.NettyZioServerInterpreter
import zio._

object Main extends ZIOAppDefault {

  val dbLayer = ZLayer.scoped(
    ZIO.acquireRelease(ZIO.attempt(Database.forConfig("payments")))
    (db => ZIO.succeed(db.close()))
  )

  override def run = {
    for {
      controller <- ZIO.service[PaymentController]
      endpoint   <- controller.endpoint
      binding    <- NettyZioServer()
        .port(8080)
        .addEndpoint(endpoint)
        .start()
      _          <- ZIO.logInfo(s"Server started on port ${binding.port}")
      _          <- ZIO.never
    } yield ()
  }.provide(
    PaymentController.layer,
    PaymentService.layer,
    PaymentDAO.layer,
    dbLayer
  )
}