package Repo

import models.Lot
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}

class LotRepo(db: Database)(implicit ec: ExecutionContext) {
  val lots = TableQuery[models.LotModel]

  def createLot(lot: Lot): Future[Int] = {
    db.run(lots += lot)
  }
  def findByLot(lotid: Int): Future[Option[Lot]] = {
    db.run(lots.filter(_.LotId===lotid).result.headOption)
  }
  def findAllLot(): Future[Seq[Lot]] = {
    db.run(lots.result)
  }
}
