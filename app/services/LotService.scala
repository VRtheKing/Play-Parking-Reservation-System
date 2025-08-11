package services

import models.Lot
import Repo.LotRepo
import scala.concurrent.{ExecutionContext, Future}
import javax.inject.{Inject, Singleton}
import redis.RedisClient
import play.api.libs.json._

@Singleton
class LotService @Inject()(lotRepo: LotRepo, redisProvider: utils.RedisClientProvider)(implicit ec: ExecutionContext) {
  private val redis = redisProvider.client
  private val lotCacheKey = "lots:all"
  private val defaultTTLSeconds = 60 // 3 minutes of Time To Live

  implicit val lotFormat: OFormat[Lot] = Json.format[Lot]

  def createLot(lot: Lot): Future[Either[String, Int]] = {
    redis.del(lotCacheKey)
    lotRepo.createLot(lot).map(Right(_))
  }

  def getAllLot: Future[Seq[Lot]] = {
    redis.get(lotCacheKey).flatMap {
      case Some(jsonData) =>
        println("Fetched from cached data")
        val jsonString = jsonData.utf8String
        val lots = Json.parse(jsonString).as[Seq[Lot]]
        Future.successful(lots)

      case None =>
        println("redis cache not available")
        lotRepo.findAllLot().flatMap { lots =>
          val lotsJson = Json.toJson(lots).toString()
          redis.set(lotCacheKey, lotsJson, exSeconds = Some(defaultTTLSeconds))
          Future.successful(lots)
        }
    }
  }
}
