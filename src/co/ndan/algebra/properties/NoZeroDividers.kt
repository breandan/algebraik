package co.ndan.algebra.properties

import java.lang.annotation.Documented

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.util.OpBinary
import co.ndan.util.OpNullary

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class NoZeroDividers {

  object Definition {
    fun <E> check(m: OpBinary<E, E, E>, eq: OpBinary<E, E, Boolean>, zero: OpNullary<E>, a: E, b: E
    ): Boolean {
      return if (eq.ap(m.ap(a, b), zero.ap()))
        eq.ap(a, zero.ap()) || eq.ap(b, zero.ap())
      else
        true
    }
  }
}
