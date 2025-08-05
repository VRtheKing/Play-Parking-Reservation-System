package Repo

import models.User
import slick.jdbc.PostgresProfile.api._
import scala.concurrent.{ExecutionContext, Future}

class UserRepo(db: Database)(implicit ec: ExecutionContext) {
  val users = TableQuery[models.UserModel]

  def createUser(user: User): Future[Int] = {
    db.run(users += user)
  }

  def findByUsername(username: String): Future[Option[User]] = {
    db.run(users.filter(_.username === username).result.headOption)
  }
}
