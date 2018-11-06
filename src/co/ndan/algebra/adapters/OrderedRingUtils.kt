package co.ndan.algebra.adapters

import co.ndan.algebra.concept.OrderedRing

/*
 * Utility class for handling ordered field operations.
 *
 */
open class OrderedRingUtils<E>(protected override val impl: OrderedRing<E>)
/*
 * This is a little tricky: the super class only uses it's impl in the
 * leq method, which we override.  We can't pass impl because OR doens't
 * implement TO (which is sort of the point of this class).
 */
  : TotalOrderUtils<E>(null), OrderedRing<E> {

  override fun minus(a: E, b: E) = plus(a, neg(b))

  fun divEq(n1: E, d1: E, n2: E, d2: E) = eq(times(n1, d2), times(n2, d1))
  override fun leq(a: E, b: E) = isNonnegative(plus(b, neg(a)))

  /*
	** pass through ************************************************************
	*/

  override fun isNonnegative(e: E) = impl.isNonnegative(e)

  override fun times(a: E, b: E) = impl.times(a, b)

  override fun one() = impl.one()

  override fun plus(a: E, b: E) = impl.plus(a, b)

  override fun neg(a: E) = impl.neg(a)

  override fun zero() = impl.zero()
}
