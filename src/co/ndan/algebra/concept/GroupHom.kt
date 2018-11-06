package co.ndan.algebra.concept

import co.ndan.algebra.properties.CommutesWith

/*
 * A group homomorphism h from a group D (the domain) to a group C (the codomain)
 * is a function that commutes with the group operation on C and D.  In other
 * words, for all a, b in D, h(a + b) = h(a) + h(b).
 *
 * @see [Group homomorphism on Wikipedia](http://en.wikipedia.org/wiki/Group_homomorphism)
 *
 *
 */
interface GroupHom<DE, CE> : SetHom<DE, CE> {
  @CommutesWith("co.ndan.algebra.concept.Group.plus")
  override fun ap(e: DE): CE

  override fun domain(): Group<DE>
  override fun codomain(): Group<CE>
}
