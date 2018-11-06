package co.ndan.algebra.properties

import java.lang.annotation.*

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.algebra.properties.meta.annotation.MethodName
import co.ndan.util.OpBinary

/*
 * A @Commutative function f : E → E → R has the property that f(a,b) == f(b,a).
 *
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class Commutative(@MethodName val eq: String = "eq") {

  object Definition {
    fun <E, R> check(m: OpBinary<E, E, R>, eq: OpBinary<R, R, Boolean>, a: E, b: E
    ): Boolean {
      return eq.ap(m.ap(a, b), m.ap(b, a))
    }
  }
}
