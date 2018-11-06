package co.ndan.algebra.construction

import co.ndan.algebra.concept.IntegralDomain
import co.ndan.algebra.concept.Ring

object Rot15 : FieldOfFractions<Rot15.Cos15e.Element, Rot15.Cos15e>(Cos15e.instance) {

  private val c15e = Cos15e
  val ROT45: Rot15.Element = INJ.ap(c15e.ROT45)
  val ROT30: Rot15.Element = INJ.ap(c15e.ROT30)
  val ROT15: Rot15.Element = times(ROT45, inv(ROT30))

  /*
	** Z[sqrt2, sqrt3] *********************************************************
	**/

  class SignatureRoot6<E, R : Ring<E>>(private val r: R) : Extension.Signature<E> {

    override fun coefficients(): Array<Array<Array<E>>> {
      val inj = Z.into(r)
      val e0 = inj.ap(0)
      val e1 = inj.ap(1)
      val e2 = inj.ap(2)
      val e3 = inj.ap(3)
      val e6 = inj.ap(6)
      val e9 = inj.ap(9)

      return arrayOf(arrayOf(arrayOf<Any>(e1, e0, e0, e0) // √1 * √1
        , arrayOf<Any>(e0, e1, e0, e0) // √1 * √2
        , arrayOf<Any>(e0, e0, e1, e0) // √1 * √3
        , arrayOf<Any>(e0, e0, e0, e1) // √1 * √6
      ),

        arrayOf(arrayOf<Any>(e0, e1, e0, e0) // √2 * √1
          , arrayOf<Any>(e2, e0, e0, e0) // √2 * √2
          , arrayOf<Any>(e0, e0, e0, e1) // √2 * √3
          , arrayOf<Any>(e0, e0, e2, e0) // √2 * √6
        ),

        arrayOf(arrayOf<Any>(e0, e0, e1, e0) // √3 * √1
          , arrayOf<Any>(e0, e0, e0, e1) // √3 * √2
          , arrayOf<Any>(e3, e0, e0, e0) // √3 * √3
          , arrayOf<Any>(e0, e3, e0, e0) // √3 * √6
        ),

        arrayOf(arrayOf<Any>(e0, e0, e0, e1) // √6 * √1
          , arrayOf<Any>(e0, e0, e2, e0) // √6 * √2
          , arrayOf<Any>(e0, e9, e0, e0) // √6 * √3
          , arrayOf<Any>(e6, e0, e0, e0) // √6 * √6
        )) as Array<Array<Array<E>>>
    }

    override fun dimension() = 4

    override fun one(): Array<E> =
      arrayOf<Any>(r.one(), r.zero(), r.zero(), r.zero()) as Array<E>

    @Throws(IllegalArgumentException::class)
    override fun generatorName(i: Int): String {
      when (i) {
        0 -> return null
        1 -> return "√2"
        2 -> return "√3"
        3 -> return "√6"
        else -> throw IllegalArgumentException()
      }
    }
  }

  object Root6 : Extension<Int, SignatureRoot6<Int, Z>>(SignatureRoot6(Z), Z), IntegralDomain<Root6.Element> {
    val SQRT1: Root6.Element = Extension.Element(1, 0, 0, 0)
    val SQRT2: Root6.Element = Extension.Element(0, 1, 0, 0)
    val SQRT3: Root6.Element = Extension.Element(0, 0, 1, 0)
    val SQRT6: Root6.Element = Extension.Element(0, 0, 0, 1)
  }

  /*
	** Field of fractions of Root6 *********************************************
	*/

  object Cos15 : FieldOfFractions<Root6.Element, Root6>(Root6.instance) {

    private val r6 = Root6
    val HALF: Cos15.Element = inv(INJ.ap(r6.INJ.ap(2)))
    val COS45: Cos15.Element = times(INJ.ap(r6.SQRT2), HALF)
    val SIN45: Cos15.Element = times(INJ.ap(r6.SQRT2), HALF)
    val COS30: Cos15.Element = times(INJ.ap(r6.SQRT3), HALF)
    val SIN30: Cos15.Element = times(INJ.ap(r6.SQRT1), HALF)
  }

  /*
	** Cos15[i] ****************************************************************
	*/

  class SignatureComplex<E, R : Ring<E>>(private val r: R) : Extension.Signature<E> {

    override fun coefficients(): Array<Array<Array<E>>> {
      val e0 = r.zero()
      val e1 = r.one()
      val n1 = r.neg(e1)

      return arrayOf(arrayOf(arrayOf<Any>(e1, e0) // 1 * 1
        , arrayOf<Any>(e0, e1) // 1 * i
      ),

        arrayOf(arrayOf<Any>(e0, e1) // i * 1
          , arrayOf<Any>(n1, e0) // i * i
        )) as Array<Array<Array<E>>>
    }

    override fun dimension() = 2

    override fun one() = arrayOf<Any>(r.one(), r.zero()) as Array<E>

    @Throws(IllegalArgumentException::class)
    override fun generatorName(i: Int): String = when (i) {
      1 -> "i"
      else -> throw IllegalArgumentException()
    }
  }

  object Cos15e : Extension<Cos15.Element, SignatureComplex<Cos15.Element, Cos15>>(SignatureComplex<Cos15.Element, Cos15>(Cos15.instance), Cos15.instance), IntegralDomain<Cos15e.Element> {
    val ROT45: Cos15e.Element = Extension.Element(Cos15.COS45, c15.SIN45)
    val ROT30: Cos15e.Element = Extension.Element(Cos15.COS30, c15.SIN30)
  }

  fun main(args: Array<String>) {
    println("Cos 30: " + Cos15.COS30)
    println("Cos 45: " + Cos15.COS45)
    println("Rot 15: " + ROT15)
  }
}
