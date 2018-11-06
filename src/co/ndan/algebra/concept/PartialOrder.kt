package co.ndan.algebra.concept

import co.ndan.algebra.properties.AntiSymmetric

/*
 * A partial order (or partially ordered set...POSET for short), is a set with
 * a a relation ⊑ that is reflexive, transitive, and anti-symmetric.
 *
 * @see [Partially Ordered Set on Wikipedia](http://en.wikipedia.org/wiki/Partially_ordered_set)
 *
 *
 */
interface PartialOrder<E> : Preorder<E> {
  @AntiSymmetric
  override fun leq(a: E, b: E): Boolean
}
