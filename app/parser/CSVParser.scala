package parser

import scala.io.Source

object CSVParser {

  def main(args: Array[String]): Unit = {
    val src = Source.fromFile("source/csv/71211-0007.csv","ISO-8859-1")
    val lines = src.getLines()
    val iter = lines.drop(5).map(_.split(";"))
    // print the uid for Guest
    
    iter.next
    iter.next
    
    iter foreach (a => {a foreach ( b => print(b + " "))
    					println("")})
    // the rest of iter is not processed
    src.close()
  }

}