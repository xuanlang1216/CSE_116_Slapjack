package tests

import THE_GAME.Game
import THE_GAME.Player
import org.scalatest.FunSuite

class TestLast_And_Player extends FunSuite{

  test ("test current player and last card on desk."){
    val Hakeem = new Player("Hakeem")
//    val Joe = new Player("Joe")
//    val Same = new NPCPlayer("Sam")
//    val Emily = new NPCPlayer("Emily")
    val games = new Game

    games.PlayerJoin(Hakeem)
    var card = Hakeem.myCards.head.toString
    games.CurrentPlayerPlay()
    assert(games.CurrentPlayer == 0, "test1")
    assert(games.DisplayLastCardOnDesk() == card, "cards1")
    card = Hakeem.myCards.head.toString
    games.CurrentPlayerPlay()
    assert(games.DisplayLastCardOnDesk() == card, "cards2")
    //assert(games.CardsOnDesk.head.toString == card, "cards1")
//    games.PlayerJoin(Joe)
//    games.CurrentPlayerPlay()
//    assert(games.CurrentPlayer == 1, "test2")
  }

}
