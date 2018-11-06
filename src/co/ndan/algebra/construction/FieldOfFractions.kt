package co.ndan.algebra.construction

import co.ndan.algebra.concept.Field
import co.ndan.algebra.concept.IntegralDomain
import co.ndan.algebra.concept.RingHom
import co.ndan.algebra.properties.meta.annotation.MagicCheck

abstract class FieldOfFractions<E, D : IntegralDomain<E>> protected constructor(private val d: D) : Field<FieldOfFractions<E, D>.Element> {
  var INJ = NaturalHom()

  inner class Element
  /* @throws IllegalArgumentException If den is zero in r.
   */
  @Throws(IllegalArgumentException::class)
  constructor(val num: E, val den: E) {
    init {
      if (d.eq(den, d.zero()))
        throw IllegalArgumentException("Division by zero")
    }

    /* A string representation of this suitable for display.  */
    override fun toString() = if (d.eq(den, d.one())) num.toString()
    else "(" + num.toString() + " / " + den.toString() + ")"
  }

  @MagicCheck
  inner class NaturalHom : RingHom<E, Element> {
    override fun domain() = d

    override fun codomain() = this@FieldOfFractions

    override fun ap(e: E) = Element(e, d.one())
  }

  override fun times(a: Element, b: Element) = Element(d.times(a.num, b.num), d.times(a.den, b.den))

  override fun one() = Element(d.one(), d.one())

  override fun plus(a: Element, b: Element) =
    Element(d.plus(d.times(a.num, b.den), d.times(b.num, a.den)), d.times(a.den, b.den))

  override fun neg(a: Element) = Element(d.neg(a.num), a.den)

  override fun zero() = Element(d.zero(), d.one())

  override fun eq(a: Element, b: Element) = d.eq(d.times(a.num, b.den), d.times(b.num, a.den))

  override fun inv(a: Element): Element {
    if (d.eq(a.num, d.zero()))
      throw IllegalArgumentException()

    return Element(a.den, a.num)
  }

  override fun fromInt(i: Int) = Element(d.fromInt(i), d.one())
}

/*
** vim: ts=4 sw=4 cindent cino=\:0
*/
