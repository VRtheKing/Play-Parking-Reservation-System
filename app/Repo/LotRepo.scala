package Repo

import models.Lot
import slick.jdbc.PostgresProfile.api._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class LotRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[PostgresProfile] {

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
