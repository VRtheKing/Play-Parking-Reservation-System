package services

import models.Reservation
import Repo.{LotRepo, ReservationRepo}
import scala.concurrent.{ExecutionContext, Future}

class ReservationService(reservationRepo: ReservationRepo,lotRepo: LotRepo)(implicit ec: ExecutionContext) {

  def createReservation(reservation: Reservation): Future[Either[String, Int]] = {
    lotRepo.findByLot(reservation.LotId).flatMap {
      case Some(_) =>
        reservationRepo.createReservation(reservation).map(Right(_))
      case None =>
        Future.successful(Left("Lot ID does not exist"))
    }
  }

  def getReservationById(reservationId: Int): Future[Option[Reservation]] = {
    reservationRepo.findByReservationId(reservationId)
  }

  def getAllReservations: Future[Seq[Reservation]] = {
    reservationRepo.findAllReservation()
  }
}
