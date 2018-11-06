package co.ndan.algebra.properties

import java.lang.annotation.*

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.algebra.properties.meta.annotation.MethodName
import co.ndan.util.OpBinary

/*
 * A function f : S → E → E that @DistributesOver a function g : E → E → E has
 * the property that
 *
 * f(a,g(b,c)) = g(f(a,b), f(a,c))
 *
 *
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class DistributesOver(@MethodName val value: String, @MethodName val eq: String = "eq") {

  object Definition {
    fun <S, E> check(m: OpBinary<S, E, E>, eq: OpBinary<E, E, Boolean>, value: OpBinary<E, E, E>, a: S, b: E, c: E
    ): Boolean {
      return eq.ap(m.ap(a, value.ap(b, c)), value.ap(m.ap(a, b), m.ap(a, c))
      )
    }
  }
}
