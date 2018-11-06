package co.ndan.algebra.properties

import co.ndan.algebra.properties.meta.annotation.MethodName
import co.ndan.util.OpBinary
import co.ndan.util.OpUnary

/* @MagicProperty */
annotation class ClosedUnder(@MethodName vararg val value: String) {

  object Definition {
    fun <E> check(m: OpUnary<E, Boolean>, value: OpBinary<E, E, E>, a: E, b: E
    ): Boolean {
      return if (m.ap(a) && m.ap(b)) m.ap(value.ap(a, b)) else true
    }
  }
}
