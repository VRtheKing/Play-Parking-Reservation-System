package Repo

import models.Reservation
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.PostgresProfile
import slick.jdbc.PostgresProfile.api._

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class ReservationRepo @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[PostgresProfile] {

  val reservation = TableQuery[models.ReservationModel]

  def createReservation(reserve: Reservation): Future[Int] = {
    db.run(reservation += reserve)
  }

  def findAllReservation(): Future[Seq[Reservation]] = {
    db.run(reservation.result)
  }

  def findByReservationId(reserveId: Int): Future[Option[Reservation]] = {
    db.run(reservation.filter(_.ReservationId === reserveId).result.headOption)
  }
}
