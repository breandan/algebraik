package co.ndan.algebra.properties

import java.lang.annotation.Documented

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.algebra.properties.meta.annotation.MethodName
import co.ndan.util.OpBinary

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class Bilinear(@MethodName val smult: String = "smult", @MethodName val eq: String = "eq") {
  object Definition {
    fun <S, E> check(m: OpBinary<E, E, E>, smult: OpBinary<S, E, E>, eq: OpBinary<E, E, Boolean>, s: S, a: E, b: E) = eq.ap(smult.ap(s, m.ap(a, b)), m.ap(smult.ap(s, a), b)) && eq.ap(smult.ap(s, m.ap(a, b)), m.ap(a, smult.ap(s, b)))
  }
}
