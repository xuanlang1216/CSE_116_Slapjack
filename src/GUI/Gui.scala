package GUI
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{TextArea, TextField}
import scalafx.scene.layout.GridPane
import THE_GAME._

object Gui extends JFXApp {

  val game = new Game
  var Player1= new Player
  //var Player2= new NPCPlayer
  game.PlayerJoin(Player1)
  //game.PlayerJoin(Player2)

  var CardDisplay :TextField = new TextField{
    editable = false
    style = "-fx-font: 18 ariel;"
  }

  var RemainingCard : TextField = new TextField{
    editable = false
    style = "-fx-font: 18 ariel;"
  }

  var PointDisplay : TextField = new TextField {
    editable = false
    style="-fx-font: 18 ariel;"
  }

  val gameinfo:TextArea=new TextArea{
    //editable=false
    style="-fx-font: 18 ariel;"
    prefHeight = 500
    prefWidth = 500
  }

  val Start_B = new StartButton(game,2 ,2)
  val Call_B = new SlapButton(game,2,2)
  val PlayCard_B = new PlayCardButton(game,2,2)

  this.stage = new PrimaryStage{
    title = "The Slapjack Game"
    scene = new Scene(1000,800){
      content = List(
         new GridPane{
           add(CardDisplay,0,2)
           add(Call_B,0,4)
           add(RemainingCard,0,5)
           add(PlayCard_B,2,4)
           add(PointDisplay,2,2)
           add(gameinfo,0,10)
        }
      )
    }

    AnimationTimer(Update).start()
  }

  def Update(time:Long):Unit={
    RemainingCard.text = "Remaining Cards:"+ Player1.myCards.length
    CardDisplay.text = game.DisplayLastCardOnDesk()
    PointDisplay.text = "Points: "+ Player1.Point.toString
    var Gameinfo:String="Cards On Desk        Player1's Cards       Player2's Cards\n"
    for (k<-0 to 100){
      if(k<game.CardsOnDesk.length){
        Gameinfo=Gameinfo+game.CardsOnDesk.apply(k).toString
      }
      Gameinfo=Gameinfo+"                       "
      if(k<game.Players.head.myCards.length){
        Gameinfo=Gameinfo+game.Players.head.myCards.apply(k).toString
      }
      Gameinfo=Gameinfo+"                   "
      /*if(k<game.Players.apply(1).myCards.length){
        Gameinfo=Gameinfo+game.Players.apply(1).myCards.apply(k).toString
      }*/
      Gameinfo=Gameinfo+"                   \n"
    }
    gameinfo.text=Gameinfo
  }
}
