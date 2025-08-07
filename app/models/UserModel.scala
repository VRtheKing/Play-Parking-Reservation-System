package models

import slick.jdbc.PostgresProfile.api._
import java.sql.Timestamp

case class User(username: String, password: String, createdAt: Timestamp, user_type: String)

class UserModel(tag:Tag) extends Table[User](tag,"users") {
  def username = column[String]("username", O.PrimaryKey)
  def password = column[String]("password")
  def createdAt = column[Timestamp]("created_at")
  def user_type = column[String]("user_type")

  def * = (username, password, createdAt, user_type) <> (User.tupled, User.unapply)
}
  