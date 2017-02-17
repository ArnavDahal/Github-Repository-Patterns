/*
* Name(s): Omaid Khan, Filip Variciuc, Arnav Dahal
* Course: CS 441 - Mark Grechanik
* Assignment: Final Project
* Date: 10 December 2016 
*
* */

package CS441Final

import CS441Final.Common._
import com.mongodb.casbah.Imports._
import scala.io.Source

// This class will extract the data from the bucket file and put it into mongoDB
object MetaDataToMongoDB extends App {

  // Name of the bucket file
  val bucketName = "payloadBucket"

  // String that holds the entire bucket file
  val bucketLoad = Source.fromFile(bucketName).getLines.mkString("\n")

  // New regular expression class
  val githubRegex = new githubRegex

  // Drops the old database
  mongoServer.collection.drop()
  LoggerClass.Log_And_Print("Old database dropped")

  // Extracts all from the saved bucket
  LoggerClass.Log_And_Print("Parsing and extracting metadata. May take up to 15 seconds...")
  val name: Array[String] = githubRegex.findMatching(githubRegex.name, bucketLoad)
  val size: Array[String] = githubRegex.findMatching(githubRegex.size, bucketLoad)
  val stargazers_count: Array[String] = githubRegex.findMatching(githubRegex.stargazers_count, bucketLoad)
  val watchers_count: Array[String] = githubRegex.findMatching(githubRegex.watchers_count, bucketLoad)
  val language: Array[String] = githubRegex.findMatching(githubRegex.language, bucketLoad)
  val has_issues: Array[String] = githubRegex.findMatching(githubRegex.has_issues, bucketLoad)
  val has_downloads: Array[String] = githubRegex.findMatching(githubRegex.has_downloads, bucketLoad)
  val has_wiki: Array[String] = githubRegex.findMatching(githubRegex.has_wiki, bucketLoad)
  val has_pages: Array[String] = githubRegex.findMatching(githubRegex.has_pages, bucketLoad)
  val forks_count: Array[String] = githubRegex.findMatching(githubRegex.forks_count, bucketLoad)
  val open_issues_count: Array[String] = githubRegex.findMatching(githubRegex.open_issues_count, bucketLoad)
  val forks: Array[String] = githubRegex.findMatching(githubRegex.forks, bucketLoad)
  val open_issues: Array[String] = githubRegex.findMatching(githubRegex.open_issues, bucketLoad)
  val watchers: Array[String] = githubRegex.findMatching(githubRegex.watchers, bucketLoad)
  val score: Array[String] = githubRegex.findMatching(githubRegex.score, bucketLoad)

  LoggerClass.Log_And_Print("Extraction complete")
  val totalProjects = name.length
  LoggerClass.Log_And_Print("There are " + totalProjects + " projects")
  LoggerClass.Log_And_Print("Now storing projects into mongoDB")

  // Loop to create a project object and save it for every project
  for (i <- 0 until totalProjects) {
    createProjectObject(name {
      i
    },
      size {
        i
      }.toInt,
      stargazers_count {
        i
      }.toInt,
      watchers_count {
        i
      }.toInt,
      language {
        i
      },
      has_issues {
        i
      },
      has_downloads {
        i
      },
      has_wiki {
        i
      },
      has_pages {
        i
      },
      forks_count {
        i
      }.toInt,
      open_issues_count {
        i
      }.toInt,
      forks {
        i
      }.toInt,
      open_issues {
        i
      }.toInt,
      watchers {
        i
      }.toInt,
      score {
        i
      }.toDouble)
  }

  LoggerClass.Log_And_Print("All projects have been stored. Now closing...")

  // Creates a new project object and calls a definition to save that object
  def createProjectObject(name: String,
                          size: Int,
                          stargazers_count: Int,
                          watchers_count: Int,
                          language: String,
                          has_issues: String,
                          has_downloads: String,
                          has_wiki: String,
                          has_pages: String,
                          forks_count: Int,
                          open_issues_count: Int,
                          forks: Int,
                          open_issues: Int,
                          watchers: Int,
                          score: Double) {

    val newProject = Projects(name,
      size,
      stargazers_count,
      watchers_count,
      language,
      has_issues,
      has_downloads,
      has_wiki,
      has_pages,
      forks_count,
      open_issues_count,
      forks,
      open_issues,
      watchers,
      score)

    saveProject(newProject)
  }

  // Saves the object into mongoDB
  def saveProject(project: Projects) {
    val mongoObj = buildProjectsObject(project) // Builds the object into a JSON
    LoggerClass.Log("Built the object into a JSON")
    mongoServer.collection.save(mongoObj)
  }
}