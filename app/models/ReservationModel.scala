package models

import slick.jdbc.PostgresProfile.api._
import java.sql.Timestamp

case class Reservation (ReservationId: Int, username: String, LotId: Int, CheckIn: Timestamp, CheckOut:Timestamp)

class ReservationModel(tag: Tag) extends Table[Reservation](tag, "reservations"){
    def ReservationId = column[Int]("reservation_id", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def LotId = column[Int]("lot_id")
    def CheckIn = column[Timestamp]("check_in")
    def CheckOut = column[Timestamp]("check_out")

    def * = (ReservationId, username, LotId, CheckIn, CheckOut) <> (Reservation.tupled, Reservation.unapply)
}
