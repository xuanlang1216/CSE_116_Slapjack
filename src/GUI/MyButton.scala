package GUI
import java.io.FileNotFoundException
import java.nio.file.{Files, Paths}
import scalafx.Includes._
import scalafx.scene.control.Button
import scala.io.Source
import THE_GAME._
import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import javafx.application.Platform
import javafx.event.ActionEvent

class MyButton (xScale:Double,yScale:Double) extends Button {
  minWidth = 100 * xScale
  minHeight = 100 * yScale
  style = "-fx-font: 12 ariel;"
}


class SlapButton(socket:Socket, xScale: Double = 1.0, yScale: Double = 1.0)extends MyButton(xScale,yScale){
  text = "Slap!"
  style = "-fx-font: 24 ariel;"
  onAction = (event:ActionEvent) => socket.emit("slap")
}

class PlayCardButton(socket:Socket, xScale: Double = 1.0, yScale: Double = 1.0)extends MyButton(xScale,yScale){
  text = "Play a Card"
  style = "-fx-font: 24 ariel;"
  onAction = (event:ActionEvent) => socket.emit("play")

}
