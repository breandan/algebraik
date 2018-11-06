package co.ndan.algebra.adapters

import java.util.Comparator

import co.ndan.algebra.concept.TotalOrder

open class TotalOrderUtils<E>(impl: TotalOrder<E>) : Comparator<E>, TotalOrder<E> {

  override fun compare(a: E, b: E) = when {
    eq(a, b) -> 0
    leq(a, b) -> -1
    else -> 1
  }

  private fun lt(a: E, b: E) = leq(a, b) && (!eq(a, b))

  fun geq(a: E, b: E) = leq(b, a)

  fun gt(a: E, b: E) = lt(b, a)

  fun min(a: E, b: E) = if (leq(a, b)) a else b

  fun max(a: E, b: E) = if (leq(a, b)) b else a

  /*
	** pass through ************************************************************
	*/

  override fun eq(a: E, b: E) = impl.eq(a, b)

  override fun leq(a: E, b: E) = impl.leq(a, b)
}
