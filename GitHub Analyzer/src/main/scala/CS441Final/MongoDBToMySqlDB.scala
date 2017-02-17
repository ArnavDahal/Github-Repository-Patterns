/*
* Name(s): Omaid Khan, Filip Variciuc, Arnav Dahal
* Course: CS 441 - Mark Grechanik
* Assignment: Final Project
* Date: 10 December 2016 
*
* */

package CS441Final


import org.json.JSONArray

// This class will pick fields that made up patterns that were interesting
// for our group and will put them into a remote relational database running MySQL
//
// These patterns are:
// size
// language
// has_issues
// has_downloads
// has_wiki
// has_pages
//
object MongoDBToMySqlDB extends App {

  //create tables
  LoggerClass.Log_And_Print("Creating database schema...")
  CreateSchema()
  LoggerClass.Log_And_Print("Done")

  //insert attributes into tables
  LoggerClass.Log_And_Print("Inserting data into tables...This may take anywhere from 10-15 minutes(parsing JSON + remote DB latency + query execution time)")
  InsertData()
  LoggerClass.Log_And_Print("Done")

  //Create the tables in the remote database
  def CreateSchema() {
    val statement = mySqlServer.connection.createStatement

    // Use FinalProjectDB as Database
    statement.execute("USE FinalProjectDB;")

    //start fresh by clearing the tables in the MySQL DB
    statement.execute("DROP TABLE IF EXISTS ProjectsTable;")
    statement.execute("DROP TABLE IF EXISTS LanguagesTable;")

    statement.execute(
      "CREATE TABLE LanguagesTable(" +
        "languagesTableID INT NOT NULL AUTO_INCREMENT," +
        "language VARCHAR(100) NOT NULL," +
        "PRIMARY KEY(languagesTableID)" +
        ");")
    statement.execute(
      "CREATE TABLE ProjectsTable(" +
        "projectsTableID INT NOT NULL AUTO_INCREMENT," +
        "_id VARCHAR(100) NOT NULL," +
        "fork_count INT NOT NULL," +
        "stargazer_count INT NOT NULL," +
        "watcher_count INT NOT NULL," +
        "size INT NOT NULL," +
        "open_issues_count INT NOT NULL," +
        "languagesTableID INT NOT NULL," +
        "has_issues BOOLEAN NOT NULL DEFAULT FALSE," +
        "has_downloads BOOLEAN NOT NULL DEFAULT FALSE," +
        "has_wiki BOOLEAN NOT NULL DEFAULT FALSE," +
        "has_pages BOOLEAN NOT NULL DEFAULT FALSE," +
        "PRIMARY KEY(projectsTableID)," +
        "FOREIGN KEY (languagesTableID) REFERENCES LanguagesTable(languagesTableID)" +
        ");")
  }

  //insert relevant attributes into relational database
  def InsertData() {

    //take all of the data from MongoDB, store it into one giant json string
    val json_string = com.mongodb.util.JSON.serialize(mongoServer.collection.toList)

    //convert json_string to an array, so it will be possible to iterate over each individual entry
    val obj = new JSONArray(json_string)

    for (i <- 0 until obj.length()) {

      //
      val json_object = obj.getJSONObject(i)

      //extract just the language field from the json object
      val language = json_object.optString("language")

      //store the language tag in another table, seperate from the rest of the data to save space in the database
      var result = mySqlServer.connection.createStatement().executeQuery("SELECT languagesTableID FROM LanguagesTable WHERE language = '" + language + "';")

      if (!result.next()) //if this language has already been seen by the database, store its languageID
      {
        //else insert this new language into the relational DB and store its languageID into
        mySqlServer.connection.createStatement().execute("INSERT INTO LanguagesTable(language) values ('" + language + "')")
        result = mySqlServer.connection.createStatement().executeQuery("SELECT languagesTableID FROM LanguagesTable WHERE language = '" + language + "';")
        result.next()
      }

      val languageID = result.getString("languagesTableID")
      val _id = json_object.getJSONObject("_id").optString("$oid")
      val forks = json_object.optString("forks")
      val stargazers_count = json_object.optString("stargazers_count")
      val watchers = json_object.optString("watchers")
      val size = json_object.optString("size")
      val open_issues_count = json_object.optString("open_issues_count")
      val has_downloads = json_object.optString("has_downloads")
      val has_issues = json_object.optString("has_issues")
      val has_wiki = json_object.optString("has_wiki")
      val has_pages = json_object.optString("has_pages")

      //store everything else into ProjectsTable. This may take a very long time, as there are 10,000 projects to parse
      mySqlServer.connection.createStatement().executeUpdate(
        "INSERT INTO FinalProjectDB.ProjectsTable(" +
          "_id, fork_count, stargazer_count, watcher_count, size, open_issues_count," +
          "languagesTableID, has_issues, has_downloads, has_wiki, has_pages) " +
          "values " +
          "('" + _id + "', " + forks + ", " + stargazers_count + ", " + watchers + ", " + size + ", " + open_issues_count +
          ", " + languageID + ", " + has_issues + ", " + has_downloads + ", " + has_wiki + ", " + has_pages + ")" +
          ";")
    }
  }
}

