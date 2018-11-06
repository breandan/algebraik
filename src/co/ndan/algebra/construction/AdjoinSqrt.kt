package co.ndan.algebra.construction

import co.ndan.algebra.concept.Algebra
import co.ndan.algebra.concept.OrderedRing
import co.ndan.algebra.numbers.NumberType

open class AdjoinSqrt<E, R, N : NumberType>(private val r: R, n: N) :
  OrderedRing<AdjoinSqrt<E, R, N>.Element>,
  Algebra<AdjoinSqrt<E, R, N>.Element, Int>
  where R : OrderedRing<E>, R : Algebra<E, Int> {
  private val n: E = Z.into(r).ap(n.value)

  inner class Element(val c1: E, val cn: E) {
    override fun toString() = when {
      eq(this, zero()) -> "0"
      r.eq(c1, r.zero()) -> cn.toString() + "√" + n
      r.eq(cn, r.zero()) -> c1.toString()
      else -> "(" + c1.toString() + " + " + cn.toString() + "√" + n + ")"
    }
  }

  fun makeElement(a1: E, an: E) = Element(a1, an)

  override fun scalars() = Z

  override fun times(a: Element, b: Element): Element {
    // (a1 + an√n) (b1 + bn√n)

    // r1 = a1 * b1 + n * an * bn
    val r1 = r.plus(r.times(a.c1, b.c1), r.times(n, r.times(a.cn, b.cn)))
    // r2 = a1 * bn + an * b1;
    val rn = r.plus(r.times(a.cn, b.c1), r.times(a.c1, b.cn))

    // r = r1 + rn*√n
    return makeElement(r1, rn)
  }

  override fun one() = makeElement(r.one(), r.zero())

  override fun plus(a: Element, b: Element) = makeElement(r.plus(a.c1, b.c1), r.plus(a.cn, b.cn))

  override fun neg(a: Element) = makeElement(r.neg(a.c1), r.neg(a.cn))

  override fun zero() = makeElement(r.zero(), r.zero())

  override fun eq(a: Element, b: Element) = r.eq(a.c1, b.c1) && r.eq(a.cn, b.cn)

  override fun isNonnegative(e: Element): Boolean {
    val nn1 = r.isNonnegative(e.c1)
    val nnn = r.isNonnegative(e.cn)

    if (nn1 && nnn)
    // both coeffs are non-negative
      return true

    if (!nn1 && !nnn)
    // both coeffs are negative
      return false

    // determinant = c1^2 - n*cn^2
    val determinant = timesConj(e)

    return if (nn1) {
      // c1 is >= 0
      // cn is <  0
      //
      // the following are equivalent:
      //     c1 + cn√n >= 0
      //            c1 >= (-cn)√n (>= 0)
      //          c1^2 >= n*cn^2
      // c1^2 - n*cn^2 >= 0
      //   determinant >= 0
      r.isNonnegative(determinant)
    } else {
      // c1 is < 0
      // cn is >= 0
      //
      // the following are equivalent:
      //     c1 + cn√n >= 0
      //          cn√n >= (-c1) (>= 0)
      //        n*cn^2 >= c1^2
      // n*cn^2 - c1^2 >= 0
      // - determinant >= 0
      r.isNonnegative(r.neg(determinant))
    }
  }

  override fun smult(s: Int, a: Element) = makeElement(r.smult(s, a.c1), r.smult(s, a.cn))

  fun conj(e: Element) = makeElement(e.c1, r.neg(e.cn))

  fun timesConj(e: Element) =// (c1 + cn√n)(c1 - cn√n) = c1^2 - n*cn^2
    r.plus(r.times(e.c1, e.c1), r.neg(r.times(n, r.times(e.cn, e.cn))))
}
