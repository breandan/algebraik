package co.ndan.algebra.adapters

import co.ndan.algebra.concept.OrderedField

class OrderedFieldUtils<E>(private override val impl: OrderedField<E>) : OrderedRingUtils<E>(impl), OrderedField<E> {

  override fun div(a: E, b: E) = times(a, inv(b))

  /*
	** pass through ************************************************************
	*/

  override fun inv(a: E) = impl.inv(a)
}
