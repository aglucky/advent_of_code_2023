// Advent of Code Day 4
// Adam Gluck

import scala.math._

class Card(
    var id: Int,
    var winners: Set[Int],
    var numbers: List[Int],
    var copies: Int = 1
):
  // Alternative constructor to create card from string
  def this(line: String) = {
    this(0, Set[Int](), List[Int]())

    // Pattern match for id
    line.split(":") match
      case Array(idString, bothNumbersList) => {
        this.id = idString.filter(_.isDigit).toInt

        // Pattern match for number lists
        bothNumbersList.split("\\|") match
          case Array(winnerList, numberList) => {
            this.winners =
              winnerList.split(" ").filter(_.length > 0).map(_.toInt).toSet
            this.numbers =
              numberList.split(" ").filter(_.length > 0).map(_.toInt).toList
          }
          case _ =>
            throw new IllegalArgumentException("Invalid numbers formatting")
      }
      case _ => throw new IllegalArgumentException("Invalid Card format")
  }

  def numWinners: Int = {
    numbers.filter(this.winners.contains(_)).length
  }

  override def toString(): String = {
    return s"${this.id}: ${this.winners.mkString(" ")} | ${this.numbers.mkString(" ")}"
  }

@main
def dayFour() = {
  // Read in file for input
  val input_path: os.Path = os.pwd / "input.txt"
  val content: String = os.read(input_path)

  // Process input into cards
  var cards = content.split("\n").map(Card(_))
  
  // Increment copies based on wins
  cards.foreach(card =>{
    for (i <- 0 until card.numWinners) do
        if ((card.id + i) < cards.length)
          cards(card.id+i).copies += card.copies
  })

  // Sum total number of card copies
  cards.foldLeft(0)((acc: Int, x: Card) => acc + x.copies)
}
