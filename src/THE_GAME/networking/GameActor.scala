package THE_GAME.networking

import akka.actor.{Actor, ActorSystem, Props}

case object SendGameState

case object Update

case class PlayCard(username:String)

case object Save

case object Setup

case class Slap(username:String)

case class AddPlayer(username:String)

case class RemovePlayer(username:String)

class GameActor extends Actor {
  var the_game = new THE_GAME.Game

  override def receive: Receive = {

    case message:PlayCard=>the_game.play(message.username)
    case message:Slap=>the_game.slap(message.username)
    case message:AddPlayer=>
        the_game.PlayerJoin(message.username)
        the_game.dealCard(message.username)
    case message:RemovePlayer=>
      the_game.PlayerLeft(message.username)

    case Setup =>

    case Update =>

    case Save =>

    case SendGameState=>sender()!GameState(the_game.toString)

  }
}
