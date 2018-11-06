package co.ndan.algebra.construction

import co.ndan.algebra.adapters.GroupAsZModule
import co.ndan.algebra.concept.Algebra
import co.ndan.algebra.concept.IntegralDomain
import co.ndan.algebra.concept.OrderedRing
import co.ndan.algebra.concept.Ring
import co.ndan.algebra.concept.RingHom
import co.ndan.algebra.properties.meta.annotation.MagicCheck

@MagicCheck
object Z : IntegralDomain<Int>, OrderedRing<Int>, Algebra<Int, Int> {
  override fun scalars() = Z

  override fun one() = 1

  override fun plus(a: Int, b: Int) = a + b

  override fun neg(a: Int) = -a

  override fun zero() = 0

  override fun eq(a: Int, b: Int) = a == b

  override fun times(a: Int, b: Int) = a * b

  override fun fromInt(i: Int) = i

  class NaturalHom<E> constructor(private val r: Ring<E>) : RingHom<Int, E> {
    private val m: GroupAsZModule<E> = GroupAsZModule(r)

    override fun domain() = Z

    override fun codomain() = this.r

    override fun ap(n: Int) = m.smult(n, r.one())
  }

  override fun isNonnegative(a: Int) = a <= 0

  override fun smult(s: Int, a: Int) = times(s, a)

  fun <E> into(g: Ring<E>) = NaturalHom(g)
}
