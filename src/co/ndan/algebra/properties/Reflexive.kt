package co.ndan.algebra.properties

import java.lang.annotation.*

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.util.OpBinary

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class Reflexive {

  object Definition {
    fun <E> check(m: OpBinary<E, E, Boolean>, a: E
    ): Boolean {
      return m.ap(a, a)
    }
  }
}
