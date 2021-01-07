import akka.actor.{ActorSystem, CoordinatedShutdown}
import akka.management.scaladsl.AkkaManagement
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

case class BindFailure(reason: Throwable) extends CoordinatedShutdown.Reason

object App {
  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem()
    implicit val ec: ExecutionContext = system.dispatcher

    val akkaManagement = AkkaManagement(system)
    val coordinatedShutdown = CoordinatedShutdown(system)

    akkaManagement
      .start()
      .onComplete {
        case Success(uri) =>
          system.log.info("HTTP (management) bound on {}", uri)

        case Failure(reason) =>
          coordinatedShutdown.run(BindFailure(reason))
      }
  }
}
