package fpinscala.datastructures

sealed trait List[+A] // `List` data type
case object Nil extends List[Nothing] // data constructor for `List`
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List { // `List` companion object
  def sum(ints: List[Int]): Int = ints match { // Pattern matching example
    case Nil => 0
    case Cons(x,xs) => x + sum(xs)
  } 
  
  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x,xs) => x * product(xs)
  }
  
  def apply[A](as: A*): List[A] = // Variadic function syntax
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
  
  val example = Cons(1, Cons(2, Cons(3, Nil))) // Creating lists
  val example2 = List(1,2,3)
  val total = sum(example)

  val x = List(1,2,3,4,5) match {
    case Cons(x, Cons(2, Cons(4, _))) => x
    case Nil => 42 
    case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
    case Cons(h, t) => h + sum(t)
    case _ => 101 
  }

  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match {
      case Nil => a2
      case Cons(h,t) => Cons(h, append(t, a2))
    }

  def foldRight[A,B](l: List[A], z: B)(f: (A, B) => B): B = // Utility functions
    l match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }
  
  def sum2(l: List[Int]) = 
    foldRight(l, 0.0)(_ + _)
  
  def product2(l: List[Double]) = 
    foldRight(l, 1.0)(_ * _)


  /**
   * Exercise 3.2
   *
   * Implement the function tail for "removing" the first element of a List.
   * Notice the function takes constant time. What are different choices
   * you could make in your implementation if the List is Nil?
   */
  def tail[A](l: List[A]): List[A] = l match {
    case Nil => Nil
    case Cons(x, xs) => xs
  }

  /**
   * Exercise 3.3
   *
   * Generalize tail to the function drop, which removes the first n elements
   * from a list.
   */
  def drop[A](l: List[A], n: Int): List[A] = l match {
    case Nil => Nil
    case Cons(x, xs) => if (n == 0) Cons(x, xs) else drop(xs, n - 1)
  }

  def dropWhile[A](l: List[A])(f: A => Boolean): List[A] = l match {
    case Cons(x, xs) if f(x) => dropWhile(xs)(f)
    case _ => l
  }

  def setHead[A](l: List[A])(h: A): List[A] = sys.error("todo")

  def init[A](l: List[A]): List[A] = sys.error("todo")

  def length[A](l: List[A]): Int = sys.error("todo")

  def foldLeft[A,B](l: List[A], z: B)(f: (B, A) => B): B = sys.error("todo")

  def map[A,B](l: List[A])(f: A => B): List[B] = sys.error("todo")
}