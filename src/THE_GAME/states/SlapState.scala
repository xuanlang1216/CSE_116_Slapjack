package THE_GAME.states

import THE_GAME._

import scala.concurrent.duration._

class SlapState(thegame:Game) extends gameState(thegame ) {
  //make all NPC Slap(between 2 to 3 second)
  var timeStart:Long=System.nanoTime()
  for(i<- 0 to thegame.Players.length-2){
    thegame.Players.apply(i).NPCSlap(thegame)
  }

  val system = akka.actor.ActorSystem("system")
  import system.dispatcher
  system.scheduler.scheduleOnce(3000 milliseconds) {
    var SlowestPlayer:Int = 1000 //assume less than 1000 players in our game
    var SlowestSlapTime:Long = 0
    var fastestPlayer:Int = 1000
    var fastestSlapTime:Long=(300000000*10000).toLong
    //find out which Player slap the fastest and slowest
    for(p<-thegame.Players.indices){
      if((thegame.Players.apply(p).LastSlaptime-timeStart)>0){
        if((thegame.Players.apply(p).LastSlaptime-timeStart)>SlowestSlapTime){
          SlowestPlayer = p
          SlowestSlapTime = thegame.Players.apply(p).LastSlaptime - timeStart
        }
        if((thegame.Players.apply(p).LastSlaptime-timeStart)<fastestSlapTime){
          fastestPlayer = p
          fastestSlapTime = thegame.Players.apply(p).LastSlaptime - timeStart
        }
      }
    }
    //distribute cards
    if(SlowestPlayer<1000) {
      if (thegame.CardsOnDesk.head.Num == 11) {
        thegame.Players.apply(SlowestPlayer).myCards = thegame.CardsOnDesk ::: thegame.Players.apply(SlowestPlayer).myCards
        thegame.CardsOnDesk = List()

      }
      else {
        thegame.Players.apply(fastestPlayer).myCards = thegame.CardsOnDesk ::: thegame.Players.apply(fastestPlayer).myCards
        thegame.CardsOnDesk = List()
        thegame.Players.apply(SlowestPlayer).shuffle()
      }
    }
    //switch to nextPlayer
    if(thegame.CurrentPlayer==(thegame.Players.length-1)){
      thegame.CurrentPlayer=0
    }
    else{
      thegame.CurrentPlayer+=1
    }
    thegame.GameState=new nonSlapState(thegame)
  }




  override def Play(): Unit ={}

  override def Slap(): Unit ={
  }

}
