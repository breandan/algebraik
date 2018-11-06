package co.ndan.algebra.concept

import co.ndan.algebra.properties.NoZeroDividers

/*
 * An integral domain is a (commutative) ring with no zero divisors.  An element
 * a of a ring R is a zero divisor if there is some b ≠ 0 in R such that ab = 0.
 *
 * @see [Integral domain on Wikipedia](http://en.wikipedia.org/wiki/Integral_domain)
 *
 *
 */
interface IntegralDomain<E> : Ring<E> {
  @NoZeroDividers
  override fun times(a: E, b: E): E
}
