package services

import models.User
import Repo.UserRepo

import java.sql.Timestamp
import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject

class UserService @Inject()(userRepo: UserRepo)(implicit ec: ExecutionContext) {
  def createUser(user: User): Future[Either[String, String]] = {
    userRepo.findByUsername(user.username).flatMap {
      case Some(_) =>
        Future.successful(Left("Username already exists"))
      case None =>
        val userWithTimestamp = user.copy(createdAt = new Timestamp(System.currentTimeMillis()))
        userRepo.createUser(userWithTimestamp).map(_ => Right(user.username))
    }
  }

  def validateUser(username: String, password: String): Future[Option[User]] = {
    userRepo.findByUsername(username).map {
      case Some(user) if user.password == password => Some(user)
      case _ => None
    }
  }

}
