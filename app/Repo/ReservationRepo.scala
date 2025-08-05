package Repo

import models.Reservation
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}

class ReservationRepo(db: Database)(implicit ec: ExecutionContext) {
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
