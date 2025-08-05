package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.Lot
import services.LotService
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class LotController @Inject()(cc: ControllerComponents, lotService: LotService)(implicit ec: ExecutionContext) extends AbstractController(cc){
  implicit val LotFormat: OFormat[Lot] = Json.format[Lot]

  // POST -> /lots
  def createLot: Action[JsValue] = Action.async(parse.json){ request =>
    request.body.validate[Lot].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "Invalid lot JSON"))),
      lotReserve => {
        lotService.createLot(lotReserve).map {
          case Right(id) => Created(Json.obj("message" -> s"Lot created with ID $id"))
          case Left(error) => BadRequest(Json.obj("error" -> error))
        }
      }
    )
  }
}
