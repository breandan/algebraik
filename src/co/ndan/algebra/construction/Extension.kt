package co.ndan.algebra.construction

import java.util.ArrayList

import co.ndan.algebra.concept.Algebra
import co.ndan.algebra.concept.Ring
import co.ndan.algebra.concept.RingHom
import co.ndan.util.Utils

abstract class Extension<E, S : Extension.Signature<E>> protected constructor(private val s: S, private val r: Ring<E>) : Algebra<Extension<E, S>.Element, E> {

  val INJ = NaturalHom()
  private val n: Int

  interface Signature<E> {
    /*
     * The number of generators of the extension as a module over R.
     */
    fun dimension(): Int

    /*
     * The structure coefficient tensor for extension. If the generators are
     * e0, e1, ..., en, then it should be the case that
     *
     * ei * ej = sum over k of (coefficients()[i][j][k] * ek).
     *
     * Each dimension of the returned array must be of size dimension().
     */
    fun coefficients(): Array<Array<Array<E>>>

    /*
     * The coefficients of R.one() as an element of the extended algebra.
     */
    fun one(): Array<E>

    /*
     * A useful string representation of the i'th generator.
     */
    @Throws(IllegalArgumentException::class)
    fun generatorName(i: Int): String
  }

  inner class Element
  /*
   * Create a new element of the extension with the given coefficients.
   * The caller is expected to not change the array after it is passed in.
   *
   * @throws IllegalArgumentException
   * if the number of coefficients is not S.dimension().
   */
  @SafeVarargs
  @Throws(IllegalArgumentException::class)
  constructor(vararg coeffs: E) {
    private val coeffs: Array<E>

    init {
      if (coeffs.size != s.dimension())
        throw IllegalArgumentException("An element of " +
          this@Extension.toString() +
          " must have " + n +
          " coefficients."
        )

      this.coeffs = coeffs

    }

    /*
     * @return the i'th coefficient of this element.
     */
    operator fun get(i: Int): E {
      if (i < 0 || i > coeffs.size)
        throw IllegalArgumentException()

      return this.coeffs[i]
    }

    /*
     * @return a useful String represenation of this element.
     */
    override fun toString(): String {
      val result = ArrayList<String>(s.dimension())
      for (i in 0 until n) {
        if (r.eq(coeffs[i], r.zero()))
          continue

        val coefficientIsOne = r.eq(coeffs[i], r.one())
        val generatorIsOne = s.generatorName(i) == null

        val term: String
        if (!coefficientIsOne && !generatorIsOne)
          term = coeffs[i].toString() + "⋅" + s.generatorName(i)
        else if (coefficientIsOne && !generatorIsOne)
          term = s.generatorName(i)
        else
          term = coeffs[i].toString()

        result.add(term)
      }

      return if (result.isEmpty())
        r.zero().toString()
      else if (result.size == 1)
        result[0]
      else
        "(" + Utils.join(result, "+") + ")"
    }
  }

  inner class NaturalHom : RingHom<E, Element> {
    override fun domain() = r

    override fun codomain() = this@Extension

    override fun ap(e: E) = smult(e, one())
  }

  init {
    this.n = s.dimension()
  }

  override fun scalars() = this.r

  override fun plus(a: Element, b: Element): Element {
    val result = makeArray()
    for (i in 0 until n)
      result[i] = r.plus(a.coeffs[i], b.coeffs[i])
    return Element(*result)
  }

  override fun neg(a: Element): Element {
    val result = makeArray()
    for (i in 0 until n)
      result[i] = r.neg(a.coeffs[i])
    return Element(*result)
  }

  override fun zero(): Element {
    val result = makeArray()
    for (i in 0 until n)
      result[i] = r.zero()
    return Element(*result)
  }

  override fun eq(a: Element, b: Element): Boolean {
    for (i in 0 until n)
      if (!r.eq(a.coeffs[i], b.coeffs[i]))
        return false
    return true
  }

  override fun times(a: Element, b: Element): Element {

    val result = makeArray()
    for (i in 0 until n)
      result[i] = r.zero()

    for (i in 0 until n)
      for (j in 0 until n)
        for (k in 0 until n)
          result[k] = r.plus(result[k], r.times(s.coefficients()[i][j][k], r.times(a.coeffs[i], b.coeffs[j])
          )
          )

    return Element(*result)
  }

  override fun one(): Element {
    return Element(*s.one())
  }

  override fun smult(s: E, a: Element): Element {
    val result = makeArray()
    for (i in 0 until n)
      result[i] = r.times(s, a.coeffs[i])
    return Element(*result)
  }

  /*
   * A string representation of this object, suitable for debugging.
   */
  override fun toString(): String {
    val names = ArrayList<String>(s.dimension())

    for (i in 0 until n)
      names.add(s.generatorName(i))

    return r.toString() + "[" + Utils.join(names, ",") + "]"
  }

  /*
   * Create a new array of elements of size n.
   * @return
   */
  private fun makeArray() = arrayOfNulls<Any>(n) as Array<E>
}
