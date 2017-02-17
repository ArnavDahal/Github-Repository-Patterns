import org.scalatest._

import CS441Final.githubRegex

import scala.io.Source

class sTest extends FlatSpec with Matchers {

  // Name of the test bucket file
  val bucketName = "testLoad"

  // String that holds the entire test bucket file
  val bucketLoad = Source.fromFile(bucketName).getLines.mkString("\n")

  // New regular expression class
  val githubRegex = new githubRegex

  // Extracts all from the saved bucket
  val name: Array[String] = githubRegex.findMatching(githubRegex.name, bucketLoad)
  val Size: Array[String] = githubRegex.findMatching(githubRegex.size, bucketLoad)
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

  "name" should "name" in {
    name {
      0
    } should be("name")
  }

  "Size" should "Size" in {
    Size {
      0
    } should be("size")
  }

  "stargazers_count" should "stargazers_count" in {
    stargazers_count {
      0
    } should be("stargazers_count")
  }

  "watchers_count" should "watchers_count" in {
    watchers_count {
      0
    } should be("watchers_count")
  }

  "language" should "language" in {
    language {
      0
    } should be("language")
  }

  "has_issues" should "has_issues" in {
    has_issues {
      0
    } should be("has_issues")
  }

  "has_downloads" should "has_downloads" in {
    has_downloads {
      0
    } should be("has_downloads")
  }


  "has_wiki" should "has_wiki" in {
    has_wiki {
      0
    } should be("has_wiki")
  }


  "has_pages" should "has_pages" in {
    has_pages {
      0
    } should be("has_pages")
  }


  "forks_count" should "forks_count" in {
    forks_count {
      0
    } should be("forks_count")
  }

  "open_issues_count" should "open_issues_count" in {
    open_issues_count {
      0
    } should be("open_issues_count")
  }

  "forks" should "forks" in {
    forks {
      0
    } should be("forks")
  }

  "open_issues" should "open_issues" in {
    open_issues {
      0
    } should be("open_issues")
  }


  "watchers" should "watchers" in {
    watchers {
      0
    } should be("watchers")
  }

  "score" should "score" in {
    score {
      0
    } should be("score")
  }


}