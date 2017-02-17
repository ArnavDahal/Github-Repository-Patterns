/*
* Name(s): Omaid Khan, Filip Variciuc, Arnav Dahal
* Course: CS 441 - Mark Grechanik
* Assignment: Final Project
* Date: 10 December 2016 
*
* */

package CS441Final

import java.sql.ResultSet
import scala.io.StdIn

//Be able to predict how big a repo will be, as well as
// how many forks, open issues, and stargazers that repo may have on average
// given a list of input parameters from the user
object ClientApplication extends App {
  println("**Welcome to our final project.**" +
    "\n\n" +
    "We will predict how large a repo will be, as well as " +
    "how many forks, open issues, and stargazers it may have (on average) given your input parameters." +
    "\n\n" +
    "For any input, you can opt to not enter anything (just press enter).\n" +
    "Giving up that restriction will loosen up the results and allow you to make new discoveries.\n")

  //ask for language, has_issues, has_downloads, has_wiki, has_pages
  println("Enter your target language " +
    "(options are [JavaScript],[Java],[Python],[Ruby],[PHP],[Cpp],[CSS],[CSharp],[C],[Scala]) :")

  val language = StdIn.readLine()
  var language_st: String = ""
  if (language.isEmpty) {
    language_st = ""
  }
  else language_st = "language=" + language

  LoggerClass.Log("User selected language: " + language_st)

  print("Has issues? (true/false) : ")
  val has_issues = StdIn.readLine()
  var has_issues_st: String = ""
  if (has_issues.isEmpty) {
    has_issues_st = ""
  }
  else has_issues_st = "has_issues=" + has_issues

  LoggerClass.Log("User selected has_issues: " + has_issues_st)

  print("Has downloads? (true/false) : ")
  val has_downloads = StdIn.readLine()
  var has_downloads_st: String = ""
  if (has_downloads.isEmpty) {
    has_downloads_st = ""
  }
  else has_downloads_st = "has_downloads=" + has_downloads

  LoggerClass.Log("User selected has_downloads: " + has_downloads_st)

  print("Has wiki? (true/false) : ")
  val has_wiki = StdIn.readLine()
  var has_wiki_st: String = ""
  if (has_wiki.isEmpty) {
    has_wiki_st = ""
  }
  else has_wiki_st = "has_wiki=" + has_wiki

  LoggerClass.Log("User selected has_wiki: " + has_wiki_st)


  print("Has pages? (true/false) : ")
  val has_pages = StdIn.readLine()
  var has_pages_st: String = ""
  if (has_pages.isEmpty) {
    has_pages_st = ""
  }
  else has_pages_st = "has_pages=" + has_pages

  LoggerClass.Log("User selected has_pages: " + has_pages_st)


  var where_clause: String = ""
  var all_params_empty: Boolean = _

  //all might be empty
  if (has_pages_st == "" && has_issues_st == "" && has_wiki_st == "" && has_downloads_st == "" && language_st == "") {
    all_params_empty = true
  }
  else {
    all_params_empty = false
    where_clause = " WHERE "
  }

  var add_more: Boolean = false

  var query: String = "SELECT COUNT(*) as num_results, AVG(fork_count) as fork, AVG(watcher_count) as watcher, AVG(size) as size, AVG(open_issues_count) as open FROM FinalProjectDB.ProjectsTable"


  //build the query piece by piece, depending on whether or not a parameter was specified
  if (!all_params_empty) {
    if (!language_st.isEmpty)
      query += " INNER JOIN LanguagesTable ON ProjectsTable.languagesTableID = LanguagesTable.languagesTableID AND LanguagesTable.language='" + language + "'"

    if (!has_pages_st.isEmpty)
      if (add_more)
        query += " AND " + has_pages_st
      else {
        query += where_clause
        query += has_pages_st
        add_more = true
      }

    if (!has_downloads_st.isEmpty)
      if (add_more)
        query += " AND " + has_downloads_st
      else {
        query += where_clause
        query += has_downloads_st
        add_more = true
      }

    if (!has_wiki_st.isEmpty)
      if (add_more)
        query += " AND " + has_wiki_st
      else {
        query += where_clause
        query += has_wiki_st
        add_more = true
      }

    if (!has_issues_st.isEmpty)
      if (add_more)
        query += " AND " + has_issues_st
      else {
        query += where_clause
        query += has_issues_st
        add_more = true
      }
  }

  //query has been fully built at this point
  LoggerClass.Log("QUERY TO EXECUTE: " + query)

  //
  // Get execution statement from MySql Server.
  // Specify use FinalProjectDb to know which of the DB to connect to
  //
  val statement = mySqlServer.connection.createStatement()
  statement.execute("USE FinalProjectDB;")

  LoggerClass.Log("Connected to FinalProjectDB: SUCCESS")

  var result: ResultSet = statement.executeQuery(query)
  var number_of_results: Int = 0

  LoggerClass.Log("Executed Query: SUCCESS")

  var avg_stargazers, avg_forks, avg_size, avg_open_issues: String = ""

  while (result.next()) {
    number_of_results = result.getString("num_results").toInt
    avg_forks = result.getString("fork")
    avg_stargazers = result.getString("watcher")
    avg_size = result.getString("size")
    avg_open_issues = result.getString("open")
  }

  LoggerClass.Log_And_Print(
    "\nBased on the analysis of [" + number_of_results + "] projects matching your specified criteria,\n" +
      " we predict that, on average, a given repo will have: \n" +
      " - " + avg_size.toDouble / 1000 + "MB of data\n" +
      " - " + avg_forks + " forks\n" +
      " - " + avg_open_issues + " open issues\n" +
      " - " + avg_stargazers + " stargazers")
}