package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.User
import services.UserService
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService)(implicit ec: ExecutionContext) extends AbstractController(cc){

  implicit val userFormat: OFormat[User] = Json.format[User]
  // POST -> Create User -> /users

  def createUser: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[User].fold(
      errors=> Future.successful(BadRequest(Json.obj("error"->"Invalid Format"))),
      user=>{
          userService.createUser(user).map{
            case Right(id) => Created(Json.obj("message" -> s"User $user Created"))
            case Left(error) => BadRequest(Json.obj("error" -> error))
          }
      }
    )
  }

  // GET -> Fetch User -> /users

  def getUser(username: String, password: String): Action[AnyContent] = Action.async { request =>
    userService.validateUser(username, password).map {
      case Some(user) => Ok(Json.toJson(user))
      case None => Unauthorized(Json.obj("error" -> "Invalid username or password"))
    }
  }
}
