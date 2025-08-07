package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.User
import services.UserService

import java.sql.Timestamp
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserController @Inject()(cc: ControllerComponents, userService: UserService)(implicit ec: ExecutionContext) extends AbstractController(cc){
  implicit val timestampFormat: Format[Timestamp] = new Format[Timestamp] {
    def reads(json: JsValue): JsResult[Timestamp] = json.validate[String].map(s => Timestamp.valueOf(s))
    def writes(ts: Timestamp): JsValue = JsString(ts.toString)
  }
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

//  def getUser(username: String, password: String): Action[AnyContent] = Action.async { request =>
//    userService.validateUser(username, password).map {
//      case Some(user) => Ok(Json.toJson(user))
//      case None => Unauthorized(Json.obj("error" -> "Invalid username or password"))
//    }
//  }

  def getUser: Action[JsValue] = Action.async(parse.json) { request =>
    val creds = for {
      username <- (request.body \ "username").asOpt[String]
      password <- (request.body \ "password").asOpt[String]
    } yield (username, password)

    creds match {
      case Some((username, password)) =>
        userService.validateUser(username, password).map {
          case Some(user) => Ok(Json.toJson(user))
          case None => Unauthorized(Json.obj("error" -> "Invalid credentials"))
        }
      case None =>
        Future.successful(BadRequest(Json.obj("error" -> "Missing username or password")))
    }
  }
}
