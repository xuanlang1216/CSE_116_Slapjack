package THE_GAME
import scala.concurrent.duration._
class NPCPlayer(name:String) extends Player(name) {




    override def Slap():Unit={
        LastSlaptime = LastSlaptime+(math.random()*100+200).toLong
    }


}
