package models

import slick.jdbc.PostgresProfile.api._
import java.sql.Timestamp

case class Lot(LotId: Int, name:String, address: String, Image: Option[String], rent: BigDecimal, capacity: Int)

class LotModel(tag: Tag) extends Table[Lot](tag, "lots"){
  def LotId = column[Int]("LotId", O.PrimaryKey)
  def name = column[String]("name")
  def address = column[String]("address")
  def image = column[Option[String]]("image")
  def rent = column[BigDecimal]("rent")
  def capacity = column[Int]("capacity")

  def * = (LotId, name, address, image, rent, capacity) <> (Lot.tupled, Lot.unapply)
}
