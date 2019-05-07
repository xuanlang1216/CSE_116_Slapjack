package THE_GAME.states

import THE_GAME._

import scala.concurrent.duration._

class SlapState(thegame:Game) extends gameState(thegame ) {
  var timeStart:Long=System.nanoTime()
  thegame.lastGameStatement="IT'S SLAP TIME NOW!"

  val system = akka.actor.ActorSystem("system")
  import system.dispatcher
  system.scheduler.scheduleOnce(5000.milliseconds) {
    GoToNextPlayer()
  }



  override def Play(id:String): Unit ={}

  override def Slap(id:String): Unit ={
    val timespend:Long=System.nanoTime()-timeStart
    thegame.lastGameStatement+= id+" slap in "+(timespend.toDouble/1000000000).toString+"s !\n"
    if(thegame.CardsOnDesk.head.Num == 11){
      if(!PlayerSlaptime.contains(id)) {
        PlayerSlaptime = PlayerSlaptime + (id -> timespend)
      }
    }
    else{
      thegame.Players(id).myCards=thegame.Players(id).myCards.:::(thegame.CardsOnDesk)
      thegame.Players(id).shuffle()
      thegame.CardsOnDesk=List()
      thegame.PassToNextPlayer()
      thegame.lastGameStatement = id+" slap in "+(timespend.toDouble/1000000000).toString+"s !\nNot a Jack!\n"+id+" gets all cards on desk.\n"
      thegame.GameState=new nonSlapState(thegame)
    }
  }

  def GoToNextPlayer():Unit= {
    if (thegame.CardsOnDesk.nonEmpty && thegame.CardsOnDesk.head.Num == 11) {
      var playerNotSlap: List[Int] = List()
      var slowestPlayer: String = ""
      var slowestSlapTime: Long = 0
      for (p <- thegame.PlayerOrder) {
        if (PlayerSlaptime.contains(p)) {
          if (PlayerSlaptime(p) > slowestSlapTime) {
            slowestPlayer = p
            slowestSlapTime = PlayerSlaptime(p)
          }
        }
        else {
          playerNotSlap = thegame.PlayerOrder.indexOf(p) :: playerNotSlap
        }
      }
      if (playerNotSlap.isEmpty) {
        thegame.Players(slowestPlayer).myCards = thegame.Players(slowestPlayer).myCards.:::(thegame.CardsOnDesk)
        thegame.Players(slowestPlayer).shuffle()
        thegame.CardsOnDesk = List()
        thegame.PassToNextPlayer()
        thegame.lastGameStatement = slowestPlayer+" slap the slowest!\nAll cards on desk is Going to "+slowestPlayer+"!\n"
        thegame.GameState = new nonSlapState(thegame)
      }
      else {
        var r = new scala.util.Random
        var unfortunatePlayer: String = thegame.PlayerOrder.apply(playerNotSlap.apply(r.nextInt(playerNotSlap.size)))
        thegame.Players(unfortunatePlayer).myCards = thegame.Players(unfortunatePlayer).myCards.:::(thegame.CardsOnDesk)
        thegame.Players(unfortunatePlayer).shuffle()
        thegame.CardsOnDesk = List()
        thegame.PassToNextPlayer()
        thegame.lastGameStatement = "All cards on desk is Going to "+unfortunatePlayer+"!\n"
        thegame.GameState = new nonSlapState(thegame)
      }
    }
    else{
      thegame.PassToNextPlayer()
      thegame.GameState=new nonSlapState(thegame)
    }

  }
}
