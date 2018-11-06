package co.ndan.algebra.construction

import co.ndan.algebra.concept.Field
import co.ndan.algebra.concept.VectorSpace
import co.ndan.algebra.numbers.NumberType

class Vectors<N : NumberType, E>(n: N, private val clz: Class<E>, private val f: Field<E>) : VectorSpace<Vectors<N, E>.Vec, E> {
  private val n = n.value

  inner class Vec {
    internal val values: Array<E> = Array.newInstance(clz, n) as Array<E>

    override fun toString(): String {
      val result = StringBuilder("[")
      for (i in 0 until n) {
        result.append(this.values[i].toString())
        if (i + 1 < n)
          result.append(", ")
      }
      result.append("]")
      return result.toString()
    }

    operator fun get(i: Int) = this.values[i]
  }

  fun make(values: Array<E>): Vec {
    if (values.size != n)
      throw IllegalArgumentException()
    val result = Vec()
    for (i in 0 until n)
      result.values[i] = values[i]
    return result
  }

  override fun smult(s: E, a: Vec): Vec {
    val result = Vec()

    for (i in 0 until n)
      result.values[i] = f.times(s, a.values[i])
    return result
  }

  override fun plus(a: Vec, b: Vec): Vec {
    val result = Vec()

    for (i in 0 until n)
      result.values[i] = f.plus(a.values[i], b.values[i])
    return result
  }

  override fun neg(a: Vec): Vec {
    val result = Vec()
    for (i in 0 until n)
      result.values[i] = f.neg(a.values[i])
    return result
  }

  override fun zero(): Vec {
    val result = Vec()

    for (i in 0 until n)
      result.values[i] = f.zero()
    return result
  }

  override fun eq(a: Vec, b: Vec): Boolean {
    for (i in 0 until n)
      if (!f.eq(a.values[i], b.values[i]))
        return false
    return true
  }

  override fun scalars() = f
}
