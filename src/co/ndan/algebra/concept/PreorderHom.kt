package co.ndan.algebra.concept

import co.ndan.algebra.properties.Preserves

interface PreorderHom<DE, CE> : SetHom<DE, CE> {
  @Preserves("co.ndan.algebra.concept.Preorder.leq")
  override fun ap(e: DE): CE

  override fun domain(): Preorder<DE>
  override fun codomain(): Preorder<CE>
}
