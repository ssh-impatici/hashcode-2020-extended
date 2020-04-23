import java.io.{File, PrintWriter}
import scala.io.Source
import scala.collection.mutable.ArrayBuffer

class Solver(var file:String) extends Thread {
  val _path : String = System.getProperty("user.dir") + "\\"

  override def run(): Unit = {
    println(s"$file : solver started execution")
    /////////////////////////////////////////// READ /////////////////////////////////////////////
    println(s"$file : reading file ...")
    val buffer = Source.fromFile(new File(_path + "\\input\\" + file))
    val lines = buffer.getLines.toList
    buffer.close
    ////////////////////////////////////////// INPUT ////////////////////////////////////////////
    val configuration = lines(0).split("\\s").map(_.toInt)
    val scores = lines(1).split("\\s").map(_.toInt)

    val numbooks = configuration(0)
    val numlibraries = configuration(1)
    val deadline = configuration(2)

    val libraries_info = lines.drop(2)
    var libraries = ArrayBuffer[Library]()

    var i = 0
    var j = 0
    while(i < libraries_info.size - 1){
      val params = libraries_info(i).split("\\s").map(_.toInt)
      val library_books = libraries_info(i+1).split("\\s").map(x => new Book(x.toInt, scores(x.toInt)))

      libraries.addOne(new Library(
        id = j,
        books = library_books.toList,
        signupDays = params(1),
        booksPerDay = params(2)
      ))
      j += 1
      i += 2
    }
    //////////////////////////////////////// ALGORITHM //////////////////////////////////////////
    // Libraries sorting
    println("ORDLIB -- Inizio Ordinamento Librerie")
    libraries = libraries.sorted.reverse
    println("ORDLIB -- Fine Ordinamento Librerie")

    // Books filtering per library
    println("BOOKS -- Filtering Books")
    var res_libraries = ArrayBuffer[Library]() // in ordine di sign up
    var duplicate_books = ArrayBuffer[Int]()
    var total_signup_days = 0
    for(library <- libraries) {
      if(total_signup_days < deadline) {
        total_signup_days += library.signupDays
        var max_sendable_books = (deadline - total_signup_days) * library.booksPerDay
        var booksToSend = ArrayBuffer[Book]()
        for(book <- library.books.sorted.reverse if max_sendable_books > 0){
          if (!duplicate_books.contains(book.id)) {
            duplicate_books.addOne(book.id)
            booksToSend.addOne(book)
            max_sendable_books -= 1
          }
        }
        library.booksToSend = booksToSend
        res_libraries.addOne(library)
      }
    }
    println("BOOKS -- Filtering Books DONE")
    ///////////////////////////////////////// OUTPUT ////////////////////////////////////////////
    val writer = new PrintWriter(new File(_path + "output\\output_" + file))
    res_libraries = res_libraries.filter(_.booksToSend.nonEmpty)
    writer.append(res_libraries.size.toString +"\n")
    for(library <- res_libraries) {
      writer.append(library.id + " " + library.booksToSend.size +"\n")
      library.booksToSend.foreach(book => writer.append(book.id + " "))
      writer.append("\n")
    }
    writer.close()
    println(s"$file : solver execution finished")
  }
}
