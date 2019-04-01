package THE_GAME.states

import THE_GAME._

class nonSlapState(thegame:Game) extends gameState(thegame ) {

  override def Play(): Unit = {
    thegame.CurrentPlayerPlay()
    thegame.GameState=new SlapState(thegame)
  }

  override def Slap(): Unit = {}

}
