package THE_GAME.states

import THE_GAME._

import scala.concurrent.duration._

class SlapState(thegame:Game) extends gameState(thegame ) {

  var timeStart:Long=System.nanoTime()
  val system = akka.actor.ActorSystem("system")
  import system.dispatcher
  system.scheduler.scheduleOnce(3000 milliseconds) {
    var SlowestPlayer:Int = 0
    var SlowestSlapTime:Long = 0
    var fastestPlayer:Int = 0
    var fastestSlapTime:Long=30000
    for(p<-thegame.Players.indices){
      if((thegame.Players.apply(p).LastSlaptime-timeStart)>0){
        if((thegame.Players.apply(p).LastSlaptime-timeStart)>SlowestSlapTime){
          SlowestPlayer = p
          SlowestSlapTime = thegame.Players.apply(p).LastSlaptime - timeStart
        }
        if((thegame.Players.apply(p).LastSlaptime-timeStart)<SlowestSlapTime){
          fastestPlayer = p
          fastestSlapTime = thegame.Players.apply(p).LastSlaptime - timeStart
        }
      }
    }
    if(thegame.CardsOnDesk.head.Num==11){
      thegame.Players.apply(SlowestPlayer).myCards=thegame.CardsOnDesk:::thegame.Players.apply(SlowestPlayer).myCards
      thegame.CardsOnDesk=List()

    }
    else{
      //thegame.Players.apply(fastestPlayer).myCards=thegame.CardsOnDesk:::thegame.Players.apply(fastestPlayer).myCards
      //thegame.CardsOnDesk=List()
      //thegame.Players.apply(SlowestPlayer).shuffle()
    }
    thegame.GameState=new nonSlapState(thegame)
  }




  override def Play(): Unit ={}

  override def Slap(): Unit ={
  }

}
