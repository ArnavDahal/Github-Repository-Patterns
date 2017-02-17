Authors:
	Arnav Dahal
	Filip Variciuc
	Omaid Khan
	
#High level overview and implementation is discussed in the SRS document.
#This README is meant to show what parts of the project are executable (and what order they should be run in).


Information:

	Our implementation involves 6 parts (All written in scala :-)) using IntelliJ IDEA:
	
		1. MetaDataDownloader.scala
			- Download projects from GitHub using GitHub API implemented via Akka Actors. Store the giant JSON payload in a payloadBucket, (one file containing metadata for many projects) saved in the projects main directory.
		2. Servers.scala
			- Establish two objects, mongoServer and mySqlServer. The mongoServer references an instance of a Virtual Machine with a MongoDB instance. The mySqlServer references a SQL Database instance. Both of these instances are running on the cloud via Google Platform (using the student access code).
		3. MetaDataToMongoDB.scala
			- Parse payloadBucket; tokenize it into many different projects. Insert each project into MongoDB server.
		4. MongoDBToMySqlDB.scala
			- Retrieve projects from MongoDB one by one, filter out unnecessary attributes, and insert into open source relational database running MySQL.
		5. LoggerClass.scala
			- Logging various operations and responses to those operations to file log.log (kept in projects main directory) using slf4j logging tool.
		6. ClientApplication.scala
			- Offer the user the possibility to enter in some constraints about a project in order to produce some estimation of repo attributes.

		
How to Run:

	**Since this project relies heavily on network communications between the JVM, GitHub, and Google alike, there are parts of this project that take a considerable amount of time to run. Expect the duration of any one part to be from 15 seconds - 1 hour**

	Run these in order to have a feel for the logical flow of the project, from raw data to a database to client application endpoint.

	Alternatively, you can also skip from step 0 to step 4, since steps 1, 2, and 3 have the databases up to date already.

		0. Build SBT and all src files by clicking Build > Make Project
		
		1. MetaDataDownloader.scala (RUNTIME: ~45 minutes)
			-Due to the rate limits GitHub imposes on developers, we could not shave the time any lower than this.
			
			-The console prints its status on every stage to not appear totally dead.
			
			-If this starts to run, any existing payloadBucket file will be deleted, and premature termination will affect the upcoming parts. You've been warned.
			
				To run, right-click MetaDataDownloader.scala, and Run 'MetaDataDownloader'.
		
		2. MetaDataToMongoDB.scala (RUNTIME: ~15-20 seconds)
			-Clearing the database, parsing all of the JSON into individual fields, and inserting them one by one into MongoDB.
					
				To run, right-click MetaDataToMongoDB.scala, and Run 'MetaDataToMongoDB'.
					
		3. MongoDBToMySqlDB.scala (RUNTIME: ~10-15 minutes)
			-By analyzing time spent in the debugger, we see that the INSERT statement at the end of this file is where most of the programs time is spent.
			
				To run, right-click MongoDBToMySqlDB.scala, and Run 'MongoDBToMySqlDB'.
					
		4. ClientApplication.scala (RUNTIME: 0 seconds, no long-duration computation here)
			
			-INPUT IS CASE SENSITIVE. THERE IS NO ERROR CHECKING FOR MISSPELLED INPUT.
			
			-The prompts in the ClientApplication are self explanitory and should walk the client through it. Basically, enter the language, whether it has issues, downloads, a wiki page, and pages (or opt to not enter anything and just press enter to impose less constraints on your dataset) to retrieve an approximation on how big a given repo will be, how many forks it will have, how many open issues it will have, and how many stargazers it will have.
			
				To run, right-click ClientApplication.scala, and Run 'ClientApplication'.

				
Tests:
	-15 Scalatests can be found and run in ./test, in the ./src directory.
	We tested the functionality of our parser using a payload where we modified the JSON node values.
	Generally, these tests are focused on ensuring the regex parser in githubRegex.scala is working as intended and correctly parsing JSON fields.
	
	-Load tests using Apache JMeter and their results are detailed in the SRS document.