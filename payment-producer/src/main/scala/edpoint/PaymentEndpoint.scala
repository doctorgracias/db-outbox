package edpoint

import model.Payment
import sttp.model.StatusCode
import sttp.tapir.{Endpoint, endpoint, statusCode, stringBody}
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.generic.auto._

object PaymentEndpoint {
  val paymentEndpoint: Endpoint[Unit, Payment, String, Unit, Any] = endpoint
    .post
    .in("payment")
    .in(jsonBody[Payment])
    .out(statusCode(StatusCode.Ok))
    .errorOut(stringBody)
}
