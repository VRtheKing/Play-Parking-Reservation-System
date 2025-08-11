package utils

import javax.inject._
import redis.RedisClient
import akka.actor.ActorSystem

@Singleton
class RedisClientProvider @Inject()(implicit system: ActorSystem) {
  val client = RedisClient("localhost", 6379)
}
