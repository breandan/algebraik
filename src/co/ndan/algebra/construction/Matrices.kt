package co.ndan.algebra.construction

import co.ndan.algebra.concept.Ring

class Matrices<E, N : Number> : Ring<Matrices<E, N>.Matrix> {
  private val r: Ring<E>
  private val size: Int = 0
  private val clz: Class<E>

  inner class Matrix private constructor() {
    /* length size*size; (i,j) entry in values[i*size + j]  */
    internal val values: Array<E> = Array.newInstance(clz, size * size) as Array<E>

    @Throws(IllegalArgumentException::class)
    constructor(values: Array<Array<E>>) : this() {

      if (values.size != size)
        throw IllegalArgumentException("Matrix<N> constructor requires an nxn array")

      for (i in 0 until size) {
        if (values[i].size != size)
          throw IllegalArgumentException("Matrix<N> constructor requires an nxn array")
        for (j in 0 until size)
          this.values[i * size + j] = values[i][j]
      }
    }

    private operator fun set(i: Int, j: Int, e: E) {
      this.values[i * size + j] = e
    }

    operator fun get(i: Int, j: Int) = values[i * size + j]

    override fun toString(): String {
      val result = StringBuilder("[")
      for (i in 0 until size) {
        result.append("[")
        for (j in 0 until size) {
          result.append(get(i, j))
          if (j + 1 < size)
            result.append(", ")
        }
        result.append("]")
        if (i + 1 < size)
          result.append(", ")
      }
      result.append("]")
      return result.toString()
    }

  }

  override fun plus(a: Matrix, b: Matrix): Matrix {
    val result = Matrix()

    for (i in 0 until size * size)
      result.values[i] = r.plus(a.values[i], b.values[i])

    return result
  }

  override fun neg(a: Matrix): Matrix {
    val result = Matrix()

    for (i in 0 until size * size)
      result.values[i] = r.neg(a.values[i])

    return result
  }

  override fun zero(): Matrix {
    val result = Matrix()

    for (i in 0 until size * size)
      result.values[i] = r.zero()
    return result
  }

  override fun eq(a: Matrix, b: Matrix): Boolean {
    for (i in 0 until size * size)
      if (a.values[i] !== b.values[i])
        return false
    return true
  }

  override fun times(a: Matrix, b: Matrix): Matrix {
    val result = Matrix()
    for (i in 0 until size)
      for (j in 0 until size) {
        var entry = r.zero()
        for (k in 0 until size)
          entry = r.plus(entry, r.times(a[i, k], b[k, j]))
        result.set(i, j, entry)
      }
    return result
  }

  override fun one(): Matrix {
    val result = Matrix()

    for (i in 0 until size)
      for (j in 0 until size)
        result.set(i, j, if (i == j) r.one() else r.zero())

    return result

  }
}
