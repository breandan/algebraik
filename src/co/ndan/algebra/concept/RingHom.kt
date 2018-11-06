package co.ndan.algebra.concept

import co.ndan.algebra.properties.CommutesWith

interface RingHom<DE, CE> : GroupHom<DE, CE> {
  @CommutesWith("co.ndan.algebra.concept.Ring.times")
  override fun ap(e: DE): CE

  override fun domain(): Ring<DE>
  override fun codomain(): Ring<CE>
}
