package models

import slick.jdbc.PostgresProfile.api._
import java.sql.Timestamp

case class Reservation (ReservationId: Int, username: String, LotId: Int, CheckIn: Timestamp, CheckOut:Timestamp)

class ReservationModel(tag: Tag) extends Table[Reservation](tag, "reservations"){
    def ReservationId = column[Int]("ReservationId", O.PrimaryKey, O.AutoInc)
    def username = column[String]("username")
    def LotId = column[Int]("LotId")
    def CheckIn = column[Timestamp]("CheckIn")
    def CheckOut = column[Timestamp]("CheckOut")

    def * = (ReservationId, username, LotId, CheckIn, CheckOut) <> (Reservation.tupled, Reservation.unapply)
}
