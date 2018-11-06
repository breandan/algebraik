package co.ndan.algebra.construction

import co.ndan.algebra.concept.Algebra
import co.ndan.algebra.concept.Field
import co.ndan.algebra.concept.OrderedRing
import co.ndan.algebra.numbers.N03
import co.ndan.algebra.numbers.N02

class Rot15Direct : OrderedRing<Rot15Direct.Element>, Field<Rot15Direct.Element>, Algebra<Rot15Direct.Element, Int> {
  private val s6: Sqrt6 = null
  private val z: Z = null

  inner class Element {
    val num: Sqrt6.Element
    val den: Int

    protected constructor(a1: Int, a2: Int, a3: Int, a6: Int, denz: Int) {
      var den = denz

      if (z.eq(den, z.zero())) throw IllegalArgumentException("Division by zero")

      var num: Sqrt6.Element = s6.makeElement(a1, a2, a3, a6)

      if (!z.isNonnegative(den)) {
        num = s6.neg(num)
        den = z.neg(den)
      }

      this.num = num
      this.den = den
    }

    constructor(num: Sqrt6.Element, den: Int) {
      this.num = num
      this.den = den
    }

    override fun toString(): String {
      val result = num.toString()
      return if (den == 1) result else "($result/$den)"
    }
  }

  protected fun makeElement(num: Sqrt6.Element, den: Int): Element {
    return Element(num, den)
  }

  override fun scalars() = Z

  override fun times(a: Element, b: Element) =
    makeElement(s6.times(a.num, b.num), z.times(a.den, b.den))

  override fun one() = makeElement(s6.one(), z.one())

  override fun plus(a: Element, b: Element) =
    makeElement(s6.plus(s6.smult(b.den, a.num), s6.smult(a.den, b.num)), z.times(a.den, b.den))

  override fun neg(a: Element) = makeElement(s6.neg(a.num), a.den)

  override fun zero() = makeElement(s6.zero(), z.one())

  override fun eq(a: Element, b: Element): Boolean =
    s6.eq(s6.smult(b.den, a.num), s6.smult(a.den, b.num))

  override fun smult(s: Int, a: Element) = makeElement(s6.smult(s, a.num), a.den)

  @Throws(IllegalArgumentException::class)
  override fun inv(a: Element) = TODO()

  override fun isNonnegative(e: Element) = s6.isNonnegative(e.num)
}

internal object Sqrt2 : AdjoinSqrt<Int, Z, N02>(Z, N02)

internal object Sqrt6 : AdjoinSqrt<Sqrt2, Sqrt2, N03>(Sqrt2, N03) {
  fun makeElement(a1: Int, a2: Int, a3: Int, a6: Int): AdjoinSqrt.Element =
    makeElement(Sqrt2.makeElement(a1, a2), Sqrt2.makeElement(a3, a6))
}

