package fpinscala.laziness

import Stream._

trait Stream[+A] {

  def uncons: Option[(A, Stream[A])]

  def isEmpty: Boolean = uncons.isEmpty

  def foldRight[B](z: => B)(f: (A, => B) => B): B =
    uncons match {
      case Some((h, t)) => f(h, t.foldRight(z)(f))
      case None => z
    }

  def exists(p: A => Boolean): Boolean = foldRight(false)((a, b) => p(a) || b)

  /**
   * Exercise 5.2:
   *
   * Write a function take for returning the first n elements of a
   * Stream.
   *
   * @param n Number of elements to take from this streams' head
   * @return Stream of n or less elements or even empty Stream
   */
  def take(n: Int): Stream[A] = uncons match {
    case Some((h, t)) if n > 0 => cons(h, t.take(n-1))
    case _ => empty
  }

  def takeWhile(p: A => Boolean): Stream[A] = sys.error("todo")

  def forAll(p: A => Boolean): Boolean = sys.error("todo")

  /**
   * Exercise 5.1:
   *
   * Write a function to convert a Stream to a List, which will
   * force its evaluation and let us look at it in the REPL. You can convert to
   * the regular List type in the standard library. You can place this and
   * other functions that accept a Stream inside the Stream trait.
   *
   * @return A list of the streams elements
   */
  def toList: List[A] = foldRight(Nil:List[A])(_ :: _)

}

object Stream {

  def empty[A]: Stream[A] = new Stream[A] { def uncons = None }
  
  def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = 
    new Stream[A] {
      lazy val uncons = Some((hd, tl)) 
    }
  
  def apply[A](as: A*): Stream[A] =
    if (as.isEmpty) empty
    else cons(as.head, apply(as.tail: _*))

  val ones: Stream[Int] = cons(1, ones)

  def from(n: Int): Stream[Int] = sys.error("todo")

  def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = sys.error("todo")

  def startsWith[A](s: Stream[A], s2: Stream[A]): Boolean = sys.error("todo")

}