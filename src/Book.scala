class Book(var id: Int, var score: Int) extends Ordered[Book]{
  def compare(that: Book): Int = this.score - that.score
}