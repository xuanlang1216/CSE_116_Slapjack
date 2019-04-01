package THE_GAME

import scala.util.Random

class Player {

   var myCards :List[Cards]= List()
   var Point:Int = 0

   def PlayCard():Cards={
         var theCard: Cards = myCards.head
         myCards = myCards.tail
         theCard
   }

   def Slap(thegame:Game):Unit={
     if(thegame.CardsOnDesk.head.Num==11){
       thegame.CardsOnDesk=List()
     }
     else{
       thegame.Players.head.myCards=thegame.CardsOnDesk:::thegame.Players.head.myCards
       thegame.CardsOnDesk=List()
     }
     shuffle()
   }

   def shuffle():Unit={
      myCards=Random.shuffle(myCards)

   }
}
