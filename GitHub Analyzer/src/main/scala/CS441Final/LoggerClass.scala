/*
* Name(s): Omaid Khan, Filip Variciuc, Arnav Dahal
* Course: CS 441 - Mark Grechanik
* Assignment: Final Project
* Date: 10 December 2016 
*
* */

package CS441Final

import grizzled.slf4j.Logger

//log to a file log.log using slf4j library, a very simple and easy logging utility for applications in the JVM
object LoggerClass {
  val logger = Logger("log")

  def Log_And_Print(s: String): Unit = {
    logger.info(s)
    println(s)
  }

  def Log(s: String): Unit = {
    logger.info(s)
  }
}
