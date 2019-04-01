package THE_GAME

import scala.util.Random

class Player {

   var myCards :List[Cards]= List()
   var Point:Int = 0
   var LastSlaptime:Long=System.nanoTime()

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
}
