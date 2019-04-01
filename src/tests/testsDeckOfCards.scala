package tests
import org.scalatest._
import THE_GAME._

class testsDeckOfCards extends FunSuite {

  /*var TestDeck :DeckOfCards= new DeckOfCards
  TestDeck.Shuffle()
  var Diamonds:Int=0
  var Spades:Int=0
  var Hearts:Int=0
  var Clubs:Int = 0
  var Jokers:Int =0
  var Player1:Player = new Player
  var Player2:Player = new Player
  var Player3:Player = new Player
  var Player4:Player = new Player
  TestDeck.deal(Player1,Player2,Player3,Player4)
  test("Shuffle"){
    assert(TestDeck.deck.length == 54,"Length")
    TestDeck.deck.foreach(i => i.suit match{
      case "D" => Diamonds+=1
      case "S" => Spades+=1
      case "H" => Hearts+=1
      case "C" => Clubs+=1
      case _ => Jokers+=1
    })
    assert(Diamonds==13,"Diamond")
    assert(Spades==13,"Spade")
    assert(Hearts==13,"Heart")
    assert(Clubs==13,"Clubs")
    assert(Jokers==2,"Joker")
  }
  var testRepeatation:Boolean = true
  //May nor test properly(numbers of cards are different)
  test("Deal"){
    assert(Player1.myCards.length==14)
    assert(Player2.myCards.length==13)
    assert(Player3.myCards.length==14)
    assert(Player4.myCards.length==13)
    Player1.myCards.foreach(x => {
      Player2.myCards.foreach(y => if (y.toString == x.toString) testRepeatation=false)
      Player3.myCards.foreach(y => if (y.toString == x.toString) testRepeatation=false)
      Player4.myCards.foreach(y => if (y.toString == x.toString) testRepeatation=false)
    })
    Player2.myCards.foreach(x => {
      Player3.myCards.foreach(y => if (y.toString == x.toString) testRepeatation=false)
      Player4.myCards.foreach(y => if (y.toString == x.toString) testRepeatation=false)
    })
    Player3.myCards.foreach(x => {
      Player4.myCards.foreach(y => if (y.toString == x.toString) testRepeatation=false)
    })
    assert(testRepeatation,"Repeated")
  }*/



}
