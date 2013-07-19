package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if(c == 0 && r == 0) 1
    else if(c < 0 || r < 0) 0
	else pascal(c,r-1) + pascal(c-1,r-1) 
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def countAlwaysPositive(chars : List[Char], count: Int): Boolean = {
      if(chars.isEmpty) count >= 0
      else count >= 0 && countAlwaysPositive(chars.tail, count + bracketCountForCharacter(chars.head)) 
    }
    
    def bracketCountForCharacter(char: Char)  = 
      (if(char == '(') 1 else (if(char == ')') -1 else 0))
    
    def bracketCount(chars : List[Char]): Int = {
      if(chars.isEmpty) 0
      else bracketCountForCharacter(chars.head) + bracketCount(chars.tail)
    }
    
    countAlwaysPositive(chars, 0) && bracketCount(chars) == 0
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if(coins.size==0) 0
    else if(money == 0) 1
    else if(coins.size==1){
      if(money % coins.head == 0) 1 else 0
    }
    else {
    	var sameMoneySmallerCoinSets = countChange(money, coins.tail)
    	var moneyPaidFromCoinHead = if(money>=coins.head) countChange(money-coins.head, coins) else 0
    	sameMoneySmallerCoinSets + moneyPaidFromCoinHead
    }
  }
}
