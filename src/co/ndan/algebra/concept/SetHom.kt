package co.ndan.algebra.concept

import co.ndan.algebra.properties.Preserves
import co.ndan.util.OpUnary

/*
 * A Set Hom is a funny name for a function: a map taking an element of a domain
 * (D) to a codomain (C).
 *
 * @see [Function on Wikipedia](http://en.wikipedia.org/wiki/Function_%28mathematics%29)
 *
 *
 */
interface SetHom<DE, CE> : OpUnary<DE, CE> {
  fun domain(): Set<DE>
  fun codomain(): Set<CE>

  @Preserves("co.ndan.algebra.concept.Set.eq")
  override fun ap(e: DE): CE
}
