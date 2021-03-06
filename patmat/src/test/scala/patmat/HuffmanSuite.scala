package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
    val tree = Fork(Leaf('k',5), Fork(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), List('e','t','x'),7),List('k','e','t','x'),12)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("times(List('a','b','a'))") {
	  assert(times(List('a', 'b', 'a')) === List(('a', 2), ('b', 1)))
  }
  
  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("singleton") {
    new TestTrees {
	  assert(singleton(List(t1)))
	  assert(!singleton(List(t1, t2)))
    }
  }
  
  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4), Leaf('k', 5))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), Leaf('k',5)))
    assert(combine(combine(leaflist)) == List(Fork(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), List('e','t','x'),7), Leaf('k',5)))
    assert(combine(combine(combine(leaflist))) === List(Fork(Leaf('k',5), Fork(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), List('e','t','x'),7),List('k','e','t','x'),12)))
  }

  test("until") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4), Leaf('k', 5))
	assert(until(singleton, combine)(leaflist) === combine(combine(combine(leaflist))))
  }

  test("create code tree") {
    val leaflist = List('k','x','k','t','e','t','k','x','k','x','k','x')
	assert(createCodeTree(leaflist)=== Fork(Leaf('k',5), Fork(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), List('e','t','x'),7),List('k','e','t','x'),12))
  }

  test("decode") {
    new TestTrees {
    	assert(decode(tree, List(1,0,1,1,1,0,1,0,1,1,0,0)) === List('t','x','k','t','e'))
    }
  }

  test("encode") {
     new TestTrees {
    	 assert(encode(tree)(List('t','x','k','t','e')) === List(1,0,1,1,1,0,1,0,1,1,0,0))
     }
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  test("codeBits") {
    val codedBits = codeBits(List(('a',List(0,0,1)),('b',List(1,1))))('b')
    assert(codedBits === List(1,1))    
  }

  test("merge code tables") {
    val firstElement = ('a',List(0,0,1))
    val secondElement = ('b',List(1,1))
    val thirdElement = ('x',List(0))
    val mergedTable = mergeCodeTables(List(firstElement,secondElement), List(thirdElement))
    assert(mergedTable.size === 3)
    assert(mergedTable.contains(firstElement))
    assert(mergedTable.contains(secondElement))
    assert(mergedTable.contains(thirdElement))
  }
  
  test("convert tree to code table") {
     new TestTrees {
       val codeTable = convert(t2)
       assert(codeTable.size === 3)
       assert(codeTable.contains(('a',List(0,0))))
       assert(codeTable.contains(('b',List(0,1))))
       assert(codeTable.contains(('d',List(1))))
     }
  }
  
  test("quick encode") {
     new TestTrees {
    	 assert(quickEncode(tree)(List('t','x','k','t','e')) === List(1,0,1,1,1,0,1,0,1,1,0,0))
     }
  }

  test("decode and quick encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, quickEncode(t1)("ab".toList)) === "ab".toList)
    }
  }

  

}
