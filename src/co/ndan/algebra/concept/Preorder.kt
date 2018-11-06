package co.ndan.algebra.concept

import co.ndan.algebra.properties.Reflexive
import co.ndan.algebra.properties.Transitive

/*
 * A pre-order is a set with a reflexitive transitive relation ⊑.  One can form
 * a partial order by taking equivalence classes of the relation ≈ defined by
 * x ≈ y if x ⊑ y and y ⊑ x
 *
 * @see [Preorder on Wikipedia](http://en.wikipedia.org/wiki/Preorder)
 *
 *
 */
interface Preorder<E> : Set<E> {
  @Reflexive
  @Transitive
  fun leq(a: E, b: E): Boolean
}
