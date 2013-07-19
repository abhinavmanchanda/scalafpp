package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CountChangeSuite extends FunSuite {
  import Main.countChange
  test("countChange: no coins") {
    assert(countChange(10,List()) === 0)
  }

  test("countChange: no money needed") {
    assert(countChange(0,List(2,3,4)) === 1)
  }

  test("countChange: one coin, same amount") {
    assert(countChange(2,List(2)) === 1)
  }

  test("countChange: one coin, multiples amount") {
    assert(countChange(4,List(2)) === 1)
  }

  test("countChange: two coins, single method each") {
    assert(countChange(2,List(2,1)) === 2)
  }

  test("countChange: two coins, combinations valid") {
    assert(countChange(4,List(1,2)) === 3)
  }

  test("countChange: no pennies") {
    assert(countChange(301,List(5,10,20,50,100,200,500)) === 0)
  }

  test("countChange: sorted CHF") {
    assert(countChange(300,List(5,10,20,50,100,200,500)) === 1022)
  }

  test("countChange: unsorted CHF") {
    assert(countChange(300,List(500,5,50,100,20,200,10)) === 1022)
  }
}
