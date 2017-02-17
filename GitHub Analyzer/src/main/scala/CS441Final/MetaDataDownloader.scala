/*
* Name(s): Omaid Khan, Filip Variciuc, Arnav Dahal
* Course: CS 441 - Mark Grechanik
* Assignment: Final Project
* Date: 10 December 2016 
*
* */

package CS441Final

import java.io.{File, FileWriter}

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import spray.json._

import scalaj.http._


case class DownloadMetaData()

object MetaDataDownloader extends App {
  // Name of the bucket file
  val system = ActorSystem("DownloadSystem")

  //define an actor to take the role of downloading all of the github meta data
  var downloader_actor: ActorRef = system.actorOf(Props[DownloadManager], "DownloadSystemActor")

  //delegate task to the actor
  val future = downloader_actor ! DownloadMetaData

  system.terminate
}


class DownloadManager extends Actor {
  override def receive: Receive = {

    case DownloadMetaData =>
      val bucketName = "payloadBucket"

      // Gets rid of old bucket file
      new File(bucketName).delete()

      // Regular Expressions class
      val githubRegex = new githubRegex

      // Wait time for Github API's rate limiting (30 requests a minute)
      val waitTime = 2500
      val limitTime = 10000
      // Creates a new bucket file
      val payloadBucket = new FileWriter(bucketName, true)

      // What (10) languages the projects that we extract metadata from will be written in
      val languages = Array("JavaScript", "Java", "Python", "Ruby", "PHP", "Cpp", "CSS", "CSharp", "C", "Scala")

      // Github client ID and Secret for authenticated API access
      val githubAuthSecret = "?client_id=c69d7273c76ecf086428&client_secret=9a2463270a5dca5e092efae035797202622c9564"

      // Page on github API
      var githubAuthPage = 1

      // The highest page that will return without error is 34
      val githubMaxPage = 34

      // API Limit request text
      val githubLimit = "API rate limit exceeded"

      // Estimate time left to parse all the Metadata
      var estimateTimeLeft = waitTime * githubMaxPage * languages.length


      for (language <- languages) {

        // Loops through the languages
        for (i <- githubAuthPage.to(githubMaxPage)) {
          // Goes through page 1-34
          // The Github RESTFul request strings
          val githubRequestString: String = "https://api.github.com/search/repositories?q=language:" + language +
            "&sort=stars" + githubAuthSecret + "&page=" + githubAuthPage.toString

          var payloadString = githubLimit
          try {
            // Gets a string payload
            payloadString = getPayload(githubRequestString)
          }
          catch {
            case e: Exception => println("Error hit; retrying")
              payloadString = githubLimit
          }

          // If rate limit is hit, it will wait until trying again
          while (payloadString contains githubLimit) {
            LoggerClass.Log_And_Print("API rate limit hit. Trying again in " + (limitTime / 1000) + " seconds")
            Thread.sleep(limitTime)
            try {
              payloadString = getPayload(githubRequestString)
            }
            catch {
              case e: Exception => println("Error hit; retrying")
                payloadString = githubLimit
            }
          }

          payloadBucket.write(payloadString) // Appends the string to the bucket file
          githubAuthPage += 1 // Increments the page
          LoggerClass.Log_And_Print("Parsing Github " + language + " metadata at page " + i +
            " | ETA : " + estimateTimeLeft / 1000 + " seconds")
          estimateTimeLeft -= waitTime

          Thread.sleep(waitTime) // Waiting to avoid Rate-limit timeout
        }
        githubAuthPage = 1

      }

      payloadBucket.close() // Closes the bucket file


      // Gets String Payload
      def getPayload(githubRequestString: String): String = {
        val payloadHTTP: HttpRequest = Http(githubRequestString)
        // Sends the HTTP Request to Github's API endpoint
        val payloadString: String = payloadToString(payloadHTTP) // Transforms the JSON payload into a string
        payloadString
      }

      // Will take in a HTTP payload convert to JSON and then return a String version
      def payloadToString(payloadHTTP: HttpRequest): String = {
        val payloadBody: String = payloadHTTP.asString.body
        val payloadJSON: JsValue = payloadBody.parseJson
        val payloadString: String = payloadJSON.prettyPrint
        payloadString
      }
  }
}
