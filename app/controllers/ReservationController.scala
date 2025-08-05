package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import models.Reservation
import services.ReservationService
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class ReservationController @Inject()(cc: ControllerComponents,reservationService: ReservationService)(implicit ec: ExecutionContext) extends AbstractController(cc) {

  implicit val reservationFormat: OFormat[Reservation] = Json.format[Reservation]

  // POST /reservations
  def createReservation: Action[JsValue] = Action.async(parse.json) { request =>
    request.body.validate[Reservation].fold(
      errors => Future.successful(BadRequest(Json.obj("error" -> "Invalid reservation JSON"))),
      reservation => {
        reservationService.createReservation(reservation).map {
          case Right(id) => Created(Json.obj("message" -> s"Reservation created with ID $id"))
          case Left(error) => BadRequest(Json.obj("error" -> error))
        }
      }
    )
  }

  // GET /reservations/:id
  def getReservation(id: Int): Action[AnyContent] = Action.async {
    reservationService.getReservationById(id).map {
      case Some(reservation) => Ok(Json.toJson(reservation))
      case None => NotFound(Json.obj("error" -> "Reservation not found"))
    }
  }

  // GET /reservations
  def getAllReservations: Action[AnyContent] = Action.async {
    reservationService.getAllReservations.map { reservations =>
      Ok(Json.toJson(reservations))
    }
  }
}
