package co.ndan.algebra.construction

import co.ndan.algebra.concept.Field

class Zp
/* p should be prime.  */
(private val p: Int) : Field<Int> {
  override fun one() = 1

  private fun reduce(x: Int): Int {
    val result = (x % p)
    return if (result < 0) p + result else result
  }

  override fun plus(a: Int, b: Int) = reduce(a + b)

  override fun neg(a: Int) = reduce(-a)

  override fun zero() = 0

  override fun eq(a: Int, b: Int) = a == b

  override fun times(a: Int, b: Int) = reduce(a * b)


  internal class Coeffs(var s: Int, var t: Int)

  /* return s and t such that gcd(a,b) = sa + tb  */
  private fun bezout(a: Int, b: Int): Coeffs {
    if (b == 0) return Coeffs(1, 0)

    val q = a / b
    val r = a % b

    val br = bezout(b, r)
    // a = qb + r,  gcd = sb + tr.  So substituting r = a - qb; gcd = ta + (s-tq)b

    val s = br.t
    val t = br.s - br.t * q
    br.s = s
    br.t = t
    return br
  }

  @Throws(IllegalArgumentException::class)
  override fun inv(a: Int): Int {
    if (a == 0) throw IllegalArgumentException()
    val result = bezout(a, p)
    // 1 = sa + tp
    return reduce(result.s)
  }

  override fun fromInt(i: Int) = reduce(i)
}
