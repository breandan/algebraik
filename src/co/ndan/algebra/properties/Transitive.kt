package co.ndan.algebra.properties

import java.lang.annotation.*

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.util.OpBinary

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class Transitive {
  object Definition {
    fun <E> check(m: OpBinary<E, E, Boolean>, a: E, b: E, c: E
    ): Boolean {
      return if (m.ap(a, b) && m.ap(b, c)) m.ap(a, c) else true
    }
  }
}
