package tests

import THE_GAME.DeckOfCards
import org.scalatest.FunSuite

class TestDealMethod extends FunSuite{

  test("Check deal of cards") {
    val cards = new DeckOfCards()
    assert(cards.deck.length == 52, "deal1")
    cards.deal()
    assert(cards.deck.length == 39, "deal2")
    cards.deal()
    assert(cards.deck.length == 26, "deal3")
    cards.deal()
    assert(cards.deck.length == 13, "deal4")
    cards.deal()
    assert(cards.deck.length == 0, "deal5")
    //assert(test2.length == 13, "test2")
  }
}
