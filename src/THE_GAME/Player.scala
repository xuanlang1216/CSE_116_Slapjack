package THE_GAME

import play.api.libs.json.{Json,JsValue}

import scala.util.Random

class Player(name:String) {

   var myCards :List[Cards]= List()
   var Point:Int = 0
   var LastSlaptime:Long=System.nanoTime()
   var userName:String=name

   def PlayCard():Cards={
         var theCard: Cards = myCards.head
         myCards = myCards.tail
         theCard
   }

   def Slap():Unit={
     LastSlaptime=System.nanoTime()
   }

   def shuffle():Unit={
      myCards=Random.shuffle(myCards)
   }

  def NPCSlap(theGame:Game):Unit={

  }

  def playerState():String={
   var card=""
    for (i<-myCards){
      card+=i.toString+" "
    }
    val playerstate:Map[String,JsValue]=Map(
      "Cards"->Json.toJson(card),
      "Points"->Json.toJson(Point)
    )
    Json.stringify(Json.toJson(playerstate))
  }

  def getCards:String={
    var CardString=""
    for(c<-myCards){
      CardString+=c.suit+c.Num+" "
    }
    CardString
  }
}
