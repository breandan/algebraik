package co.ndan.algebra.construction

import co.ndan.algebra.concept.Field

class Floats : Field<Float> {
  val threshold = 0.0001f
  override fun one() = 1f

  override fun plus(a: Float, b: Float) = a + b

  override fun neg(a: Float) = -a

  override fun zero() = 0f

  override fun eq(a: Float, b: Float) = -threshold < a - b && a - b < threshold

  override fun times(a: Float, b: Float) = a * b

  override fun inv(a: Float): Float {
    if (a == 0f) throw IllegalArgumentException("Divide by zero")

    return 1 / a
  }

  override fun fromInt(i: Int) = i.toFloat()
}
