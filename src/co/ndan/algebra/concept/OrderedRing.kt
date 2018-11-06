package co.ndan.algebra.concept

import co.ndan.algebra.properties.ClosedUnder
import co.ndan.algebra.properties.ContainsSquares
import co.ndan.algebra.properties.NoNegOne

/*
 *
 * An Ordered Ring is a ring coupled with a total order structure that is
 * compatible with the ring operations in the following sense:
 *
 *  1. If 0 ≤ a and 0 ≤ b then 0 ≤ ab
 *  1. If a ≤ b then a + c ≤ b + c
 *
 *
 *
 *
 * This interface uses an equivalent definition based on *positive
 * cones*. A positive cone is a partition of a ring into negative and
 * nonnegative elements, subject to the following constraints:
 *
 *  1. -1 ∉ P
 *  1. a² ∈ P
 *  1. P is closed under + and *
 *
 * Every positive cone induces a total order and vice-versa.
 *
 *
 * @see [Ordered Ring on Wikipedia](http://en.wikipedia.org/wiki/Ordered_ring)
 *
 * @see [Positive Cone on Wikipedia](http://en.wikipedia.org/wiki/Ordered_field#Positive_cone)
 *
 *
 */
interface OrderedRing<E> : Ring<E> {
  @NoNegOne
  @ContainsSquares
  @ClosedUnder("plus", "times")
  fun isNonnegative(e: E): Boolean
}
