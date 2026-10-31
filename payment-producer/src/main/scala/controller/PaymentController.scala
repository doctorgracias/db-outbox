package controller


import edpoint.PaymentEndpoint
import service.PaymentService
import sttp.tapir.ztapir.{RichZEndpoint, ZServerEndpoint}
import zio.{ZIO, ZLayer}

class PaymentController {
  val endpoint: ZIO[PaymentService, Nothing, ZServerEndpoint[Any, Any]] =
    ZIO.serviceWith[PaymentService] { service =>
      PaymentEndpoint.paymentEndpoint.zServerLogic { payment =>
        service.process(payment)
          .mapError(_.getMessage)
      }
    }
}

object PaymentController {
  val layer = ZLayer.succeed(new PaymentController())
}
