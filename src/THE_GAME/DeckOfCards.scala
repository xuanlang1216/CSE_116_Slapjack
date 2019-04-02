package THE_GAME

import scala.util.Random

class DeckOfCards {
 var deck:List[Cards]= List()
 for ( i <- 0 to 12){
    deck = new Cards("S",i+1)::deck
    deck = new Cards("D",i+1)::deck
    deck= new Cards("H",i+1)::deck
    deck = new Cards("C",i+1)::deck
 }
  Shuffle()

 def Shuffle():Unit= {
   deck=Random.shuffle(deck)
 }

  //deal cards : Player take 13 cards from the deck and the deck loses 13 cards
  def deal():List[Cards]={
    val cards:List[Cards] = deck.take(13)
    deck=deck.takeRight(deck.length-13)
    cards
  }

  override def toString: String = {
    var output =""
    for (i <- deck){
      output += i.Num + i.suit +"\n"
    }
    output
  }




}
