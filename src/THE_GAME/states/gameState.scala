package THE_GAME.states

import THE_GAME._

abstract class gameState(thegame:Game) {

  def Play(id:String):Unit

  def Slap(id:String):Unit
}
