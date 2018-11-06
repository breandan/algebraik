package co.ndan.algebra.concept

/*
 * A vector space is nothing more than a module over a field.  But the more
 * usual definition is that it is a set of vectors that can be added and
 * multiplied by scalars drawn from a field.
 *
 * @see [Vector space on Wikipedia](http://en.wikipedia.org/wiki/Vector_space)
 *
 *
 */
interface VectorSpace<V, S> : Module<V, S> {
  override fun scalars(): Field<S>
}
