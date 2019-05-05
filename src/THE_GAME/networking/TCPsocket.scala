package THE_GAME.networking
import java.net.InetSocketAddress

import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}
import akka.io.{IO, Tcp}
import akka.util.ByteString
import THE_GAME.Game
import play.api.libs.json.{JsValue, Json}

case object UpdateGames

case object AutoSave

case class GameState(gameState: String)

class TCPsocket(gameActor:ActorRef) extends Actor {
  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 8000))

  var clients: Set[ActorRef] = Set()

  val delimiter = "~"
  var buffer:String=""

  override def receive: Receive = {

    //    Example of adding an actor with this actor as its supervisor
    //    Note that we use the context of this actor and do not create a new actor system
    //   val childActor = context.actorOf(Props(classOf[GameActor], username))


    case b: Bound => println("Listening on port: " + b.localAddress.getPort)
    case c: Connected =>
      println("Client Connected: " + c.remoteAddress)
      this.clients = this.clients + sender()
      sender() ! Register(self)

    case PeerClosed =>
      println("Client Disconnected: " + sender())
      this.clients = this.clients - sender()

    case r: Received =>
      println("Received: " + r.data.utf8String)
      buffer+=r.data.utf8String
      while(buffer.contains(delimiter)) {
        val curr = buffer.substring(0, buffer.indexOf(delimiter))
        buffer = buffer.substring(buffer.indexOf(delimiter) + 1)
        val username = getUsername(curr)
        val the_action = getAction(curr)
        the_action match {
          case "connected" => gameActor ! AddPlayer(username)
          case "disconnected" => gameActor ! RemovePlayer(username)
          case "play" => gameActor ! PlayCard(username)
          case "slap" => gameActor ! Slap(username)
        }
      }

    case SendGameState=>gameActor!SendGameState

    case gs: GameState =>
      println(gs.gameState)
      this.clients.foreach((client: ActorRef) => client ! Write(ByteString(gs.gameState+delimiter)))
  }

  def getAction(JSString:String):String={
    val parsed:JsValue=Json.parse(JSString)
    (parsed\"action").as[String]
  }

  def getUsername(JSString:String):String={
    val parsed:JsValue=Json.parse(JSString)
    (parsed\"username").as[String]
  }


}


object TCPsocket {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    import actorSystem.dispatcher
    import scala.concurrent.duration._

    import scala.concurrent.duration._
    val gameActor=actorSystem.actorOf(Props(classOf[GameActor]))
    val server = actorSystem.actorOf(Props(classOf[TCPsocket],gameActor))

    actorSystem.scheduler.schedule(32.milliseconds, 32.milliseconds, server, SendGameState)
    //actorSystem.scheduler.schedule(0.milliseconds, 5000.milliseconds, server, AutoSave)

  }
}
