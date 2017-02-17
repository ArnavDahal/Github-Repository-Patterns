/*
* Name(s): Omaid Khan, Filip Variciuc, Arnav Dahal
* Course: CS 441 - Mark Grechanik
* Assignment: Final Project
* Date: 10 December 2016 
*
* */

package CS441Final

import java.sql.DriverManager
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.MongoConnection

// Creates the connection to the remote MongoDB database
object mongoServer {
  private val SERVER = "130.211.210.118:27017"
  private val DATABASE = "githubMetadata"
  val COLLECTION = "Projects"
  val connection = MongoConnection(SERVER)
  val collection: MongoCollection = connection(DATABASE)(COLLECTION)

  LoggerClass.Log("mongoServer server connected successfully")
}

// Creates the connection to the remote MySQL database
object mySqlServer {
  val url = "jdbc:mysql://104.198.171.51/mysql?useSSL=false"
  val driver = "com.mysql.cj.jdbc.Driver"
  val username = "root"
  val password = "password"
  var connection = DriverManager.getConnection(url, username, password)

  LoggerClass.Log("mySql server connected successfully")
}