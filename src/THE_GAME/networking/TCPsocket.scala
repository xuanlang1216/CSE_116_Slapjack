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

class TCPsocket extends Actor {
  import Tcp._
  import context.system

  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 8000))

  var clients: Set[ActorRef] = Set()
  var clients2: Map[String,ActorRef]=Map()

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
      val username=getUsername(r.data.utf8String)
      val the_action=getAction(r.data.utf8String)

    case UpdateGames =>
      this.clients2.foreach((e:(String,ActorRef)) => e._2 ! Update)
    case AutoSave =>
      this.clients2.foreach((e:(String,ActorRef)) => e._2 ! Save)
    case gs: GameState =>
      val delimiter = "~"
      println("Sending: " + gs.gameState+delimiter)
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

  def getEquipment(JSString:String):String={
    val parsed:JsValue=Json.parse(JSString)
    (parsed\"equipmentID").as[String]
  }

}


object ClickerServer {

  def main(args: Array[String]): Unit = {
    val actorSystem = ActorSystem()

    import actorSystem.dispatcher

    import scala.concurrent.duration._

    val server = actorSystem.actorOf(Props(classOf[TCPsocket]))

    actorSystem.scheduler.schedule(0 milliseconds, 100 milliseconds, server, UpdateGames)
    actorSystem.scheduler.schedule(0 milliseconds, 5000 milliseconds, server, AutoSave)

  }
}
