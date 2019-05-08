package THE_GAME.networking

import java.sql.{Connection, DriverManager, ResultSet}

import THE_GAME.{Cards, Game, Player}


object Database {
  val url = "jdbc:mysql://localhost:3306/mysql"
  val username = "root"
  val password = "12345678"
  var connection: Connection = DriverManager.getConnection(url, username, password)


  setupTable()

  def setupTable(): Unit = {
    val statement = connection.createStatement()
    statement.execute("CREATE TABLE IF NOT EXISTS players (username TEXT, Point Int, Cards TEXT)")
  }



  def playerExists(username: String): Boolean = {
    val statement = connection.prepareStatement("SELECT * FROM players WHERE username=?")

    statement.setString(1, username)
    val result: ResultSet = statement.executeQuery()

    result.next()
  }


  def createPlayer(username: String): Unit = {
    val statement = connection.prepareStatement("INSERT INTO players VALUE (?, ?, ?)")

    statement.setString(1, username)
    statement.setDouble(2, 0.0)
    statement.setString(3,"")

    statement.execute()
  }


  def saveGame(username: String, Point:Int, Cards:String): Unit = {
    val statement = connection.prepareStatement("UPDATE players SET Point = ?, Cards = ? WHERE username = ?")

    statement.setInt(1, Point)
    statement.setString(2, Cards)
    statement.setString(6, username)

    statement.execute()
  }


  def loadGame(username: String, Player:Player): Unit = {
    val statement = connection.prepareStatement("SELECT * FROM players WHERE username=?")
    statement.setString(1, username)
    val result: ResultSet = statement.executeQuery()

    result.next()
    Player.Point=result.getInt("Point")
    val PlayerCard:List[String]=result.getString("Cards").split(" ").toList
    for(c<-PlayerCard){
      Player.myCards=new Cards(c.substring(0,1),c.substring(1,2).toInt)::Player.myCards
    }
  }
}
