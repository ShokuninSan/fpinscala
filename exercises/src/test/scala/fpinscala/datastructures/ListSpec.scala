package fpinscala.datastructures

import org.scalatest.{Ignore, BeforeAndAfter, FunSuite}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import fpinscala.datastructures.List._
import scala.annotation.tailrec

@RunWith(classOf[JUnitRunner])
class ListSpec extends FunSuite with BeforeAndAfter {

  var list: List[Int] = _

  before {
    list = List(1,2,3,4,5)
  }

  /**
   * This functions helps on creation of large lists.
   *
   * The creation of large lists, e.g. by List(1 to 10000: _*), i.e. calling
   * fpinscala.datastructure.List.apply() would cause a StackOverflowError, since
   * the implementation of apply not tail-recursive.
   *
   * @param n
   * @return a list of n integers
   */
  private def createIntList(n: Int): List[Int] = {
    @tailrec
    def go(l: List[Int], n: Int): List[Int] = {
      if (n == 0) l
      else go(List.append(Cons(n, Nil), l), n - 1)
    }
    go(Nil, n)
  }

  test("Pattern matches (exercise 3.1)") {
    val x = list match {
      case Cons(x, Cons(2, Cons(4, _))) => x
      case Nil => 42
      case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
      case Cons(h, t) => h + sum(t)
      case _ => 101
    }
    assert(x === 3)
  }

  test("Tail of a non-empty list") {
    assert(List.tail(list) === List(2,3,4,5))
  }

  test("Tail of an empty list") {
    assert(List.tail(List()) === Nil)
  }

  test("Drop 5 elements from a list") {
    assert(List.drop(list, 5) === Nil)
  }

  test("Drop 1 element from a list") {
    assert(List.drop(list, 1) === List(2,3,4,5))
  }

  test("Drop element from an empty list") {
    assert(List.drop(Nil, 1) == Nil)
  }

  test("dropWhile numbers are odd") {
    val drop = List.dropWhile(list) _
    assert(drop(x => (x % 2 != 0)) === List(2,3,4,5))
  }

  test("dropWhile numbers are even") {
    val drop = List.dropWhile(List(2,4,6,9,11)) _
    assert(drop(x => (x % 2 == 0)) === List(9, 11))
  }

  test("dropWhile on empty list") {
    val drop = List.dropWhile(List()) _
    assert(drop(x => true) === Nil)
  }

  test("Replace the first element of a non-empty list") {
    assert(List.setHead(list)(42) === List(42,2,3,4,5))
  }

  test("Replace the first element of an empty list") {
    assert(List.setHead(Nil)(42) === Nil)
  }

  test("Return all but the last element of a list") {
    assert(List.init(list) === List(1,2,3,4))
  }

  test("Compute the length of a list") {
    assert(List.length(list) === 5)
  }

  test("Compute the length of an empty list") {
    assert(List.length(Nil) === 0)
  }

  test("Compute the length of a large list using the not tail-recursive function foldRight behind the scene") {
    intercept[java.lang.StackOverflowError] {
      List.length(createIntList(100000))
    }
  }

  test("Compute the length of a list using (tail-recursive) foldLeft") {
    assert(List.foldLeft(createIntList(100000), 0)((acc, _) => acc + 1) === 100000)
  }

  test("Compute the sum of a list using foldLeft") {
    assert(List.sum3(list) === 15)
  }

  test("Compute the product of a list using foldLeft") {
    assert(List.product3(List(1.0, 2.0, 3.0, 4.0, 5.0)) === 120.0)
  }

  test("Compute the length of a list using foldLeft") {
    assert(List.length3(list) === 5)
  }

  test("Reverse a list") {
    assert(List.reverse(list) === List(5, 4, 3, 2, 1))
  }

  ignore("Compute the length of a list using foldLeft2 which is written in terms of foldRight") {
    intercept[java.lang.StackOverflowError] {
      assert(List.foldLeft2(createIntList(100000), 0)((acc, _) => acc + 1) === 100000)
    }
  }

  test("Compute the length of a list using foldRight2 which is written in terms of the tail-recursive foldLeft") {
    assert(List.foldRight2(createIntList(100000), 0)((_, acc) => acc + 1) === 100000)
  }

  ignore("Result of foldLeft and foldLeft2 is equal") {
    val l = List("s", "a", "n")
    assert(List.foldLeft(l, "")(_+_) === List.foldLeft2(l, "")(_+_))
  }

  test("Result of foldRight and foldRight2 is equal") {
    val l = List("s", "a", "n")
    assert(List.foldRight(l, "")(_+_) === List.foldRight2(l, "")(_+_))
  }

  test("Append an item to list with foldRight under the hood") {
    assert(Cons(1, Cons(2, Nil)) === List.append2(Cons(1, Nil), Cons(2, Nil)))
  }

  test("Append an item to an empty list with foldRight under the hood") {
    assert(Cons(1, Nil) === List.append(Nil, Cons(1, Nil)))
  }

  test("Concatenate a list of lists into a single list") {
    val l = List(List(1,2,3), List(4,5,6))
    assert(List.concat(l) === Cons(1, Cons(2, Cons(3, Cons(4, Cons(5, Cons(6, Nil)))))))
  }

  test("Add 1 to each element of a list") {
    assert(List(2,3,4,5,6) == List.add1(list))
  }

  test("Convert List[Double] to List[String]") {
    assert(List.doubleToString(List(1.0, 2.0)) === List("1.0", "2.0"))
  }

  test("Add 1 to each element of a list using map") {
    assert(List(2,3,4,5,6) === List.map(list)(_ + 1))
  }

  test("Convert List[Double] to List[String] using map") {
    assert(List("1", "2", "3", "4", "5") === List.map(list)(_ toString))
  }

  test("Filter even number from a list") {
    assert(List(2,4) === List.filter(list)(_ % 2 == 0))
  }

  test("Filter even numbers form a list with foldRight under the hood") {
    assert(List(2,4) === List.filter2(list)(_ % 2 == 0))
  }

  test("flatMap in terms of foldRight") {
    assert(flatMap(List(1,2,3))(i => List(i,i)) === List(1,1,2,2,3,3))
  }

  test("flatMap2 in terms of concat") {
    assert(flatMap2(List(1,2,3))(i => List(i,i)) === List(1,1,2,2,3,3))
  }

  test("Filter even numbers form a list with flatMap under the hood") {
    assert(List(2,4) === List.filter3(list)(_ % 2 == 0))
  }

  test("Combine two lists of Integers") {
    assert(List.addIntLists(List(1,2,3), List(4,5,6)) === List(5,7,9))
  }

  test("Combine two lists of Integers of different length") {
    assert(List.addIntLists(List(1,2), List(4,5,6)) === List(5,7))
  }

  test("Combine two lists of Integers of different length (2)") {
    assert(List.addIntLists(List(1,2,3), List(4,5)) === List(5,7))
  }

  test("Combine two lists of Integers in terms of generic function combineLists") {
    assert(List.zipWith(List(1,2,3), List(4,5,6))(_+_) === List(5,7,9))
  }

  test("Combine two lists of Integers of different length in terms of generic function combineLists") {
    assert(List.zipWith(List(1,2), List(4,5,6))(_+_) === List(5,7))
  }

  test("Combine two lists of Integers of different length (2) in terms of generic function combineLists") {
    assert(List.zipWith(List(1,2,3), List(4,5))(_*_) === List(4,10))
  }

  ignore("Verify if a list contains another list as a subsequence") {
    assert(List.hasSubsequence(list, List(3,4)))
  }
}
