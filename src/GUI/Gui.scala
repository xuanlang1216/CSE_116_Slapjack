package GUI
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{TextArea, TextField}
import scalafx.scene.layout.GridPane
import THE_GAME._
import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import javafx.application.Platform
import javafx.event.ActionEvent
import play.api.libs.json.{JsValue, Json}

class HandleMessagesFromPython() extends Emitter.Listener {
  override def call(objects: Object*): Unit = {
    // Use runLater when interacting with the GUI
    Platform.runLater(() => {
      val jsonGameState = objects.apply(0).toString
     // println(jsonGameState)
      val gameState: JsValue = Json.parse(jsonGameState)
      val CardOnDesk = (gameState \ "CardOnDesk").as[String]
      val playerinfo = (gameState \ "playerinfo").as[JsValue]
      val AllPLAYER=(gameState\"playerinfo").as[Map[String,JsValue]]
      var PLAYERINFO=""
      for((k,v)<-AllPLAYER){
        PLAYERINFO+=k+ Json.stringify(v)+"\n"
      }
      val lastCardOnDesk=(gameState\"lastcard").as[String]
      val myuserinfo=(playerinfo\"myUsername").as[JsValue]
      val myuserpoint=(myuserinfo\"Points").as[Int]
      val game_state=(gameState\"laststatement").as[String]
      val RemainCard=(gameState\"NumberCardOnDesk").as[Int]
      val LeaderBroad=(gameState\"LeaderBroad").as[String]
      Gui.RemainingCard.text="There are "+RemainCard.toString+" cards on Desk"
      Gui.PointDisplay.text="Your Points: "+myuserpoint.toString
      Gui.CardDisplay.text=lastCardOnDesk
      var GameInfo="Card on Desk: "+ CardOnDesk+"\n"+PLAYERINFO
      //Gui.gameinfo.text=GameInfo
      Gui.gameinfo.text=LeaderBroad
      Gui.gamestatement.text=game_state
    })

  }
}
object Gui extends JFXApp {
  var socket: Socket = IO.socket("http://localhost:8080/")

  socket.connect()
  socket.emit("register", "myUsername")
  socket.on("message", new HandleMessagesFromPython)
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
    editable=false
    style="-fx-font: 18 ariel;"
    prefHeight = 500
    prefWidth = 500
  }
  val gamestatement:TextArea=new TextArea{
    editable=false
    style="-fx-font: 18 ariel;"
    prefHeight = 500
    prefWidth = 500
  }

  //val Start_B = new StartButton(socket,2 ,2)
  val Call_B = new SlapButton(socket,2,2)
  val PlayCard_B = new PlayCardButton(socket,2,2)

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
           add(gamestatement,2,10)
        }
      )
    }
  }

}
