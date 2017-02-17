/*
* Name(s): Omaid Khan, Filip Variciuc, Arnav Dahal
* Course: CS 441 - Mark Grechanik
* Assignment: Final Project
* Date: 10 December 2016 
*
* */

package CS441Final


import scala.util.matching.Regex

// This class will hold the methods for extracting data using regular expressions
class githubRegex {
  // The regular expression patterns for each metadata type
  val name:String = "(?<=(\\\"name\\\": \")).*(?=\\\",)"
  val size:String = "(?<=(\\\"size\\\": )).*(?=\\,)"
  val stargazers_count:String = "(?<=(\\\"stargazers_count\\\": )).*(?=\\,)"
  val watchers_count:String = "(?<=(\\\"watchers_count\\\": )).*(?=\\,)"
  val language:String = "(?<=(\\\"language\\\": \")).*(?=\\\",)"
  val has_issues:String = "(?<=(\\\"has_issues\\\": )).*(?=\\,)"
  val has_downloads:String = "(?<=(\\\"has_downloads\\\": )).*(?=\\,)"
  val has_wiki:String = "(?<=(\\\"has_wiki\\\": )).*(?=\\,)"
  val has_pages:String = "(?<=(\\\"has_pages\\\": )).*(?=\\,)"
  val forks_count:String = "(?<=(\\\"forks_count\\\": )).*(?=\\,)"
  val open_issues_count:String = "(?<=(\\\"open_issues_count\\\": )).*(?=\\,)"
  val forks:String = "(?<=(\\\"forks\\\": )).*(?=\\,)"
  val open_issues:String = "(?<=(\\\"open_issues\\\": )).*(?=\\,)"
  val watchers:String = "(?<=(\\\"watchers\\\": )).*(?=\\,)"
  val score:String = "(?<=(\\\"score\\\": )).*(?=\\,)"


  // findMatching will take return an Array of matching Strings
  def findMatching(gitRegex: String, gitMetadata : String): Array[String] = {

    // The Regular Expression Pattern
    val gitPattern = new Regex(gitRegex)

    // Returns RegexMatchIterator
    val gitFoundIt = gitPattern findAllIn gitMetadata

    // Converts the iterator to an Array
    val gitFoundArray = gitFoundIt.toArray

    // Returns the array
    gitFoundArray

  }
}
