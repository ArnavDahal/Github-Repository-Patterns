/*
* Name(s): Omaid Khan, Filip Variciuc, Arnav Dahal
* Course: CS 441 - Mark Grechanik
* Assignment: Final Project
* Date: 10 December 2016 
*
* */

package CS441Final

import com.mongodb.casbah.Imports._

case class Projects(name:String,
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
                    score: Double)
object Common {

  def buildProjectsObject(project: Projects): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += "name" -> project.name
    builder += "size" -> project.size
    builder += "stargazers_count" -> project.stargazers_count
    builder += "watchers_count" -> project.watchers_count
    builder += "language" -> project.language
    builder += "has_issues" -> project.has_issues
    builder += "has_downloads" -> project.has_downloads
    builder += "has_wiki" -> project.has_wiki
    builder += "has_pages" -> project.has_pages
    builder += "forks_count" -> project.forks_count
    builder += "open_issues_count" -> project.open_issues_count
    builder += "forks" -> project.forks
    builder += "open_issues" -> project.open_issues
    builder += "watchers" -> project.watchers
    builder += "score" -> project.score
    builder.result
  }

}
