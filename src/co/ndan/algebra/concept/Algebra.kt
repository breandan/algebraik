package co.ndan.algebra.concept

import co.ndan.algebra.properties.Bilinear

/*
 * An algebra over a ring R is an R-module with an R-bilinear multiplication
 * operation.  This operation induces a ring structure on the algebra.
 * In this library, all algebras are associative and unital.
 *
 * @see [Associative Algebra on Wikipedia](http://en.wikipedia.org/wiki/Associative_algebra)
 *
 *
 */
interface Algebra<E, S> : Module<E, S>, Ring<E> {
  @Bilinear
  override fun times(a: E, b: E): E
}
