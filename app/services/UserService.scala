package services

import models.User
import Repo.UserRepo
import scala.concurrent.{ExecutionContext, Future}
import javax.inject.Inject

class UserService @Inject()(userRepo: UserRepo)(implicit ec: ExecutionContext) {
  def createUser(user: User): Future[Either[String, String]] = {
    userRepo.findByUsername(user.username).flatMap {
      case Some(_) =>
        Future.successful(Left("Username already exists"))
      case None =>
        userRepo.createUser(user).map(_ => Right(user.username))
    }
  }

  def validateUser(username: String, password: String): Future[Option[User]] = {
    userRepo.findByUsername(username).map {
      case Some(user) if user.password == password => Some(user)
      case _ => None
    }
  }

}
