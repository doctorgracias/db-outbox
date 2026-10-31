
import config.MyConnection
import dao.PaymentDAO
import model.Payment
import service.PollingService
import zio._

object Main  extends ZIOAppDefault{

  override def run: ZIO[Any, Throwable, Unit] = (for {
    polling <- ZIO.service[PollingService]
    _ <- polling.poll
  }yield()).provide(
    PollingService.layer,
    PaymentDAO.layer,
    MyConnection.dbLayer
  )
}

