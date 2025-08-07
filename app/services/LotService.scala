package services

import models.Lot
import Repo.LotRepo
import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject

class LotService @Inject()(lotRepo: LotRepo)(implicit ec: ExecutionContext) {
  def createLot(lot: Lot): Future[Either[String, Int]] = {
    lotRepo.createLot(lot).map(Right(_))
  }
  def getAllLot: Future[Seq[Lot]] = {
    lotRepo.findAllLot()
  }
}
