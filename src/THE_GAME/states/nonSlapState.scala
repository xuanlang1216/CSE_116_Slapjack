package THE_GAME.states

import THE_GAME._

class nonSlapState(thegame:Game) extends gameState(thegame) {
  thegame.lastGameStatement+="It's time for "+ thegame.CurrentPlayer+" to play!\n"
  override def Play(id:String): Unit = {
    if (thegame.CurrentPlayer == id) {
      if (thegame.Players(id).myCards.nonEmpty) {
        thegame.CardsOnDesk = thegame.Players(id).PlayCard() :: thegame.CardsOnDesk
      }
      else {
        thegame.Players(id).Point += 1
        thegame.dealCard(id)
        thegame.CardsOnDesk = thegame.Players(id).PlayCard() :: thegame.CardsOnDesk
      }
      thegame.lastGameStatement+=id+" played!"
      thegame.GameState = new SlapState(thegame)
    }
  }
  override def Slap(id:String): Unit = {
    thegame.Players(id).myCards=thegame.Players(id).myCards.:::(thegame.CardsOnDesk)
    thegame.Players(id).shuffle()
    thegame.CardsOnDesk=List()
    thegame.lastGameStatement+=id+" slap at the wrong time!\nAll cards on desk is Going to "+id+"!\n"
  }

}
