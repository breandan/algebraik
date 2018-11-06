package co.ndan.geometry.util

import java.util.NoSuchElementException

abstract class AbstractForwardCirculator<E> @Throws(NoSuchElementException::class)
protected constructor(protected var current: E?) : ForwardCirculator<E> {

  init {
    if (current == null)
      throw NoSuchElementException()
  }

  override fun get(): E {
    return current
  }
}
