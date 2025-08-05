package services

import models.Lot
import Repo.LotRepo
import scala.concurrent.{ExecutionContext, Future}


class LotService(lotRepo: LotRepo)(implicit ec: ExecutionContext) {
  def createLot(lot: Lot): Future[Either[String, Int]] = {
    lotRepo.findByLot(lot.LotId).flatMap{
      case Some(_) =>
        Future.successful(Left(s"Lot Already Exists with id: ${lot.LotId}"))
      case None =>
        lotRepo.createLot(lot).map(Right(_))
    }
  }
}
