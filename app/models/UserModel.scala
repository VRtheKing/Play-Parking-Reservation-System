package models

import slick.jdbc.PostgresProfile.api._
import java.sql.Timestamp

case class User(username: String, password: String, createdAt: Timestamp)

class UserModel(tag:Tag) extends Table[User](tag,"users") {
  def username = column[String]("username", O.PrimaryKey)
  def password = column[String]("password")
  def createdAt = column[Timestamp]("createdAt")

  def * = (username, password, createdAt) <> (User.tupled, User.unapply)
}
  