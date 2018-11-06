package co.ndan.algebra.util

import co.ndan.algebra.construction.Vectors
import co.ndan.algebra.construction.Zp
import co.ndan.algebra.numbers.N10
import co.ndan.algebra.numbers.NumberType
import java.util.*

class LinearEquations<N : NumberType, E>(private val v: Vectors<N, E>, n: N) {
  private val f = v.scalars()
  private val n = n.value

  private fun getFirstVar(v: Vectors.Vec): Int {
    for (i in 0 until n)
      if (!f.eq(f.zero(), v.get(i)))
        return i
    return n
  }

  fun reduce(eqns: ArrayList<Vectors.Vec>) {
    for (i in eqns.indices) {

      // find pivot (remaining row with leftmost non-zero term)
      var nextVar = getFirstVar(eqns[i])
      var nextIndex = i
      for (j in i + 1 until eqns.size) {
        val `var` = getFirstVar(eqns[j])
        if (`var` < nextVar) {
          nextVar = `var`
          nextIndex = j
        }
      }

      if (nextVar == n)
      // all remaining rows contain only 0s.
        break

      // swap it into place i and normalize
      val tmp = eqns[nextIndex]
      eqns[nextIndex] = eqns[i]
      eqns[i] = v.smult(f.inv(tmp.get(nextVar)), tmp)

      // nullify remainder of nextVar's column
      for (j in i + 1 until eqns.size)
      // eqj = eqj - (coeff j)(eqn i)
        eqns[j] = v.plus(eqns[j], v.smult(f.neg(eqns[j].get(nextVar)), eqns[i]))
    }

    // array is in row-echelon form.  Back-substitute to get RRE form

    for (i in eqns.indices.reversed()) {
      val `var` = getFirstVar(eqns[i])
      for (j in 0 until i)
        eqns[j] = v.plus(eqns[j], v.smult(f.neg(eqns[j].get(`var`)), eqns[i]))
    }
  }

  companion object {

    @JvmStatic
    fun main(args: Array<String>) {
      val inteqns = arrayOf(
        // a, b, c,
        // d, e, f,
        // g, h, i
        intArrayOf(1, 1, 0, 1, 0, 0, 0, 0, 0, 0), // eqn a
        intArrayOf(1, 1, 1, 0, 1, 0, 0, 0, 0, 0), // eqn b
        intArrayOf(0, 1, 1, 0, 0, 1, 0, 0, 0, 0), // eqn c
        intArrayOf(1, 0, 0, 1, 1, 0, 1, 0, 0, 0), // eqn d
        intArrayOf(1, 0, 1, 0, 1, 0, 1, 0, 1, 0), // eqn e
        intArrayOf(0, 0, 1, 0, 1, 1, 0, 0, 1, 0), // eqn f
        intArrayOf(0, 0, 0, 1, 0, 0, 1, 1, 0, 2), // eqn g
        intArrayOf(0, 0, 0, 0, 1, 0, 1, 1, 1, 0), // eqn h
        intArrayOf(0, 0, 0, 0, 0, 1, 0, 1, 1, 0)  // eqn i
      )

      val v = Vectors(N10, Int::class.java, Zp(3))
      val f = v.scalars()

      val eqns = ArrayList<Vectors.Vec>()

      for (i in 0..8) {
        val coeffs = arrayOfNulls<Int>(10)
        for (j in 0..9)
          coeffs[j] = inteqns[i][j]
        val eqn = v.make(coeffs)
        eqns.add(eqn)
      }

      val le = LinearEquations(v, N10)
      le.reduce(eqns)
      for (o in eqns) println(o.toString())
    }
  }
}