package co.ndan.algebra.concept

import co.ndan.algebra.properties.Total

/*
 * A totally ordered set is partially ordered set with the property that every
 * two elements are related (i.e. either a ⊑ b or b ⊑ a).
 *
 * @see [Total order on Wikipedia](http://en.wikipedia.org/wiki/Total_order)
 *
 * @see java.util.Comparator
 *
 *
 */
interface TotalOrder<E> : PartialOrder<E> {
  @Total
  override fun leq(a: E, b: E): Boolean
}
