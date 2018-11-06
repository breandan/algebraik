package co.ndan.algebra.concept

/*
 * A module is a generalization of a vector space where scalars are drawn from
 * a ring instead of a field.
 *
 * @see [Module on Wikipedia](http://en.wikipedia.org/wiki/Module_%28mathematics%29)
 *
 *
 */
interface Module<E, S> : Group<E> {
  // TODO: properties!
  fun smult(s: S, a: E): E

  fun scalars(): Ring<S>
}
