package THE_GAME
import scala.concurrent.duration._
class NPCPlayer extends Player {




    override def Slap():Unit={
        val system = akka.actor.ActorSystem("system")
        import system.dispatcher
        system.scheduler.scheduleOnce(3000 milliseconds) {
        LastSlaptime += math.random()*100+2
        }
    }


}
