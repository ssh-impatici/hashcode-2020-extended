import scala.collection.mutable.ArrayBuffer

class Library(var id : Int, var books : List[Book], var signupDays : Int, var booksPerDay : Int) extends Ordered[Library]{
  var booksToSend = new ArrayBuffer[Book]()
  var numBooks = this.books.length

  def libraryScore(): Int = {
    val thisBooksScore = this.books.foldLeft(0)(_ + _.score)
    this.booksPerDay * thisBooksScore / this.signupDays
  }

  def compare(that: Library): Int = this.libraryScore - that.libraryScore
  override def toString = id.toString + " :" + "NumBooks: " + numBooks + ", SignUp: " + signupDays + ", BxD: " + booksPerDay
}