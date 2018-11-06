package co.ndan.algebra.properties

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.algebra.properties.meta.annotation.MethodName
import java.lang.annotation.Documented

import co.ndan.util.OpBinary

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class AntiSymmetric(@MethodName val eq: String = "eq") {

  object Definition {
    fun <E> check(m: OpBinary<E, E, Boolean>, eq: OpBinary<E, E, Boolean>, a: E, b: E
    ): Boolean {
      return if (m.ap(a, b) && m.ap(b, a))
        eq.ap(a, b)
      else
        true
    }
  }
}
