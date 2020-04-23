object Main {
  def main(args: Array[String]): Unit = {
    val files: List[String] = List[String](  "a_example.txt",
      "b_read_on.txt",
      "c_incunabula.txt",
      "d_tough_choices.txt",
      "e_so_many_books.txt",
      "f_libraries_of_the_world.txt")
    files.foreach(file => new Solver(file).start())
  }
}
