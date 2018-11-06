package co.ndan.algebra.concept

import co.ndan.algebra.properties.Inverse

/*
 * A field is a (commutative) ring where every non-zero element has a
 * multiplicative inverse.
 *
 * @see [Field on Wikipedia](http://en.wikipedia.org/wiki/Field_%28mathematics%29)
 *
 *
 */
interface Field<E> : IntegralDomain<E> {
  @Inverse("inv")
  override fun times(a: E, b: E): E

  /* @throws IllegalArgumentException if a is zero.
   */
  @Throws(IllegalArgumentException::class)
  fun inv(a: E): E

  open fun div(a: E, b: E) = times(a, inv(b))
}
