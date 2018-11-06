package co.ndan.algebra.properties

import java.lang.annotation.*

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.algebra.properties.meta.annotation.MethodName
import co.ndan.util.OpBinary
import co.ndan.util.OpNullary

/*
 * A function f : E → E → R with @Identity function g : E has the property that
 *
 * f(a, g()) = f(g(),a) = a
 *
 *
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class Identity(@MethodName val value: String, @MethodName val eq: String = "eq") {

  object Definition {
    fun <E> check(m: OpBinary<E, E, E>, eq: OpBinary<E, E, Boolean>, value: OpNullary<E>, a: E
    ): Boolean {
      return eq.ap(a, m.ap(value.ap(), a)) && eq.ap(a, m.ap(a, value.ap()))
    }
  }
}
