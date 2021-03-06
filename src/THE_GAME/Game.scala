package THE_GAME

import THE_GAME.states._
import play.api.libs.json.{JsValue, Json}
import scala.collection.mutable.ListBuffer

class Game {

  var CardsOnDesk :List[Cards] =  List()

  var Players:Map[String,Player]=Map()

  var currentTime:Long = System.nanoTime()

  var dealCards:DeckOfCards=new DeckOfCards

  var CurrentPlayer:String=""

  var GameState:gameState= new nonSlapState(this)

  var lastGameStatement:String=""

  var PlayerOrder:ListBuffer[String]=new ListBuffer[String]()

  //deal 13 cards to each player
  def start():Unit={

  }
   //need testing
  def dealCard(id:String):Unit={
    if(dealCards.deck.nonEmpty){
      Players(id).myCards=dealCards.deal()
    }
    else
    {
      dealCards=new DeckOfCards
      Players(id).myCards=dealCards.deal()
    }
  }

  def PlayerJoin(id:String):Unit={
     val player=new Player(id)
     Players+=(id->player)
     if(Players.size==1) {
       CurrentPlayer = id
     }
     PlayerOrder+=id
  }
  def PlayerLeft(id:String):Unit={
     Players-=id
     PlayerOrder-=id
  }
  //need testing
  /*def CurrentPlayerPlay():Unit={
      if(Players.apply(CurrentPlayer).myCards.nonEmpty){
        CardsOnDesk=Players.apply(CurrentPlayer).PlayCard(this)::CardsOnDesk
      }
      else{
        Players.apply(CurrentPlayer).Point+=1
        dealCard(Players.apply(CurrentPlayer))
        CardsOnDesk=Players.apply(CurrentPlayer).PlayCard(this)::CardsOnDesk
      }
  }*/
  def play(id:String): Unit ={
    GameState.Play(id)
  }

  def slap(id:String):Unit={
    GameState.Slap(id)
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

  def PassToNextPlayer():Unit={
    if((PlayerOrder.indexOf(CurrentPlayer)+1)==PlayerOrder.size){
      CurrentPlayer=PlayerOrder.head
    }
    else{
      CurrentPlayer=PlayerOrder.apply(PlayerOrder.indexOf(CurrentPlayer)+1)
    }
  }

  def update(time:Long):Unit={

  }
  def getLeaderbroad:String={
    var leaderbroad:String="Leader Broard\n"
    var leader:ListBuffer[String]=PlayerOrder.sortWith(Players(_).Point>Players(_).Point)
    for(i<-leader){
      leaderbroad+=i+" :Cards: "+Players(i).myCards.size+" Points: "+Players(i).Point+"\n"
    }
    leaderbroad
  }

  override def toString: String = {
    var cardondesk=""
    var playerState:Map[String,JsValue]=Map()
    for(i<-CardsOnDesk){
      cardondesk+=i.toString+" "
    }

    var gameState:Map[String,JsValue]=Map(
      "CardOnDesk"->Json.toJson(cardondesk),
      "NumberCardOnDesk"->Json.toJson(CardsOnDesk.size),
      "LeaderBroad"->Json.toJson(getLeaderbroad)
    )
    for((k,p)<-Players){
      var card=""
      for (i<-p.myCards){
        card+=i.toString+" "
      }
      val playerstate:Map[String,JsValue]=Map(
        "Cards"->Json.toJson(card),
        "Points"->Json.toJson(p.Point)
      )
      playerState=playerState+(k->Json.toJson(playerstate))
    }
    gameState=gameState+("playerinfo"->Json.toJson(playerState))
    gameState=gameState+("lastcard"->Json.toJson(DisplayLastCardOnDesk()))
    gameState=gameState+("laststatement"->Json.toJson(lastGameStatement))
    Json.stringify(Json.toJson(gameState))
  }





}
