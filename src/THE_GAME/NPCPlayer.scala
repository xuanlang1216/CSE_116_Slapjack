package THE_GAME
import scala.concurrent.duration._
class NPCPlayer(name:String) extends Player(name) {




     override def NPCSlap(theGame:Game):Unit={
         if(theGame.CardsOnDesk.head.Num==11) {
             LastSlaptime = System.nanoTime() + (math.random() * 1000000000 + 2000000000).toLong
         }
    }


}
