package co.ndan.algebra.adapters

import co.ndan.algebra.concept.Group
import co.ndan.algebra.concept.Module
import co.ndan.algebra.construction.Z

class GroupAsZModule<E>(private val g: Group<E>) : Module<E, Int> {
  override fun scalars() = Z

  override fun plus(a: E, b: E) = g.plus(a, b)

  override fun neg(a: E) = g.neg(a)

  override fun zero() = g.zero()

  override fun eq(a: E, b: E) = g.eq(a, b)

  override fun smult(nz: Int, az: E): E {
    var n = nz
    var a = az
    if (n < 0) {
      n = (-n)
      a = neg(a)
    }

    var result = g.zero()

    var i = n
    while (i > 0) {
      if (i and 0x01 == 1)
        result = g.plus(result, a)
      i = i shr 1
      a = g.plus(a, a)
    }

    return result
  }
}
