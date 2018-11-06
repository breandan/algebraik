package co.ndan.algebra.concept

import co.ndan.algebra.properties.*

/*
 * A (unital, commutative) ring is an abelian group with an additional
 * operation ⋅ that is associative, commutative, has an identity (1), and
 * distributes over plus.
 *
 * @see [Commutative Ring on Wikipedia](http://en.wikipedia.org/wiki/Commutative_ring)
 *
 *
 */
interface Ring<E> : Group<E> {
  @Associative
  @Commutative
  @Identity("one")
  @DistributesOver("plus")
  fun times(a: E, b: E): E

  fun one(): E

  open fun fromInt(i: Int): E {
    if (i < 0)
      return neg(fromInt(-i))
    if (i == 0)
      return zero()

    val rest = fromInt(i / 2)
    return plus(plus(rest, rest), if (i % 2 == 0) zero() else one())
  }
}
