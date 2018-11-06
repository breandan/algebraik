package co.ndan.algebra.concept

import co.ndan.algebra.properties.*

/*
 * A group is a set paired with an associative operation "plus" which has an
 * identity elemnt "zero", and in which every element has an inverse.  In this
 * library, all Groups are Abelian, which means that + is also commutative.
 *
 * @see [Abelian Group on Wikipedia](http://en.wikipedia.org/wiki/Abelian_group)
 *
 *
 */
interface Group<E> : Set<E> {
  @Commutative
  @Associative
  @Identity("zero")
  @Inverse("neg")
  fun plus(a: E, b: E): E

  fun neg(a: E): E

  fun zero(): E

  open fun minus(a: E, b: E) = plus(a, neg(b))
}
