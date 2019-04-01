package THE_GAME

import THE_GAME.states._

class Game {

  var CardsOnDesk :List[Cards] =  List()

  var Players:List[Player]=List()

  var currentTime:Long = System.nanoTime()

  var dealCards:DeckOfCards=new DeckOfCards

  var CurrentPlayer:Int =0

  var GameState:gameState= new nonSlapState(this)

  var lastUpdateTime:Long=System.nanoTime()

  //deal 13 cards to each player
  def start():Unit={
     for (i<- Players){
       dealCard(i)
     }
  }
   //need testing
  def dealCard(thePlayer:Player):Unit={
    if(dealCards.deck.nonEmpty){
      thePlayer.myCards=dealCards.deal()
    }
    else
    {
      dealCards=new DeckOfCards
      thePlayer.myCards=dealCards.deal()
    }
  }

  def PlayerJoin(Join:Player):Unit={
     Players=Join::Players
     dealCard(Players.head)

  }
  //need testing
  def CurrentPlayerPlay():Unit={
      if(Players.apply(CurrentPlayer).myCards.nonEmpty){
        CardsOnDesk=Players.apply(CurrentPlayer).PlayCard(this)::CardsOnDesk
      }
      else{
        Players.apply(CurrentPlayer).Point+=1
        dealCard(Players.apply(CurrentPlayer))
        CardsOnDesk=Players.apply(CurrentPlayer).PlayCard(this)::CardsOnDesk
      }
  }
 //need testing
  def DisplayLastCardOnDesk():String={
    if (CardsOnDesk.nonEmpty){
      CardsOnDesk.head.toString
    }
    else{
      "No Card on Desk"
    }

  }

  def update(time:Long):Unit={

  }

  override def toString: String = {
    var gameInfo:String="Cards on Desk: "
    for(c<-CardsOnDesk) {
        gameInfo=gameInfo+" "+c.toString+" "
      }
    gameInfo=gameInfo+"\n"
    for(p<-Players){
      gameInfo=gameInfo+p.userName+":( "+p.myCards.length+") "
      for(q<-p.myCards){
        gameInfo=gameInfo+" "+q.toString+" "
      }
      gameInfo=gameInfo+"\n"
    }
    gameInfo
  }





}
