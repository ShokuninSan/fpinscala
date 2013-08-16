package fpinscala.laziness

import org.scalatest.{BeforeAndAfter, FunSuite}

class StreamSpec extends FunSuite with BeforeAndAfter {

  val stream = Stream(1,2,3)
  val emptyStream = Stream.empty

  test("A stream should be converted into a list") {
    assert(stream.toList === List(1,2,3))
  }

  test("Take the first n elements of a stream") {
    assert(stream.take(2).toList === List(1,2))
  }

  test("Take the first n elements of an empty stream") {
    assert(emptyStream.take(2).toList === Nil)
  }

  test("Take 4 elements of a stream with 3 elements") {
    assert(stream.take(4).toList === List(1,2,3))
  }

  test("Take while predicate matches") {
    assert(stream.takeWhile(_ <= 2).toList === List(1,2))
  }

  test("Take while non of the elements within the stream match the predicate") {
    assert(stream.takeWhile(_ == 0).toList === Nil)
  }

  test("The given predicate does not match for all elements in the Stream") {
    assert(stream.forAll(_ % 2 == 0) === false)
  }

  test("The given predicate does match for elements in the Stream") {
    assert(stream.forAll(_ < 10) === true)
  }

  test("Take while predicate matches with takeWhile implemented in terms of foldRight") {
    assert(stream.takeWhile(_ <= 2).toList === List(1,2))
  }

  test("Take while non of the elements within the stream match the predicate with takeWhile implemented in terms of foldRight") {
    assert(stream.takeWhile(_ == 0).toList === Nil)
  }
}