package co.ndan.algebra.properties

import java.lang.annotation.*

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.algebra.properties.meta.annotation.MethodName
import co.ndan.util.OpBinary


/*
 * An @Associative function f : E → E → E has the property that
 *
 * f(a, f(b,c)) = f(f(a,b), c)
 *
 *
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class Associative(@MethodName val eq: String = "eq") {
  object Definition {
    fun <E> check(m: OpBinary<E, E, E>, eq: OpBinary<E, E, Boolean>, a: E, b: E, c: E) = eq.ap(m.ap(a, m.ap(b, c)), m.ap(m.ap(a, b), c))
  }
}
