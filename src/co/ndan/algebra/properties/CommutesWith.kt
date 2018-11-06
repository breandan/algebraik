package co.ndan.algebra.properties

import java.lang.annotation.Documented

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.algebra.properties.meta.annotation.MethodDup
import co.ndan.algebra.properties.meta.annotation.MethodExt
import co.ndan.algebra.properties.meta.annotation.MethodName
import co.ndan.util.OpNullary
import co.ndan.util.OpTernary
import co.ndan.util.OpUnary

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class CommutesWith(@MethodExt val value: String, @MethodDup val codValue: String = "value", @MethodExt val eq: String = "co.ndan.algebra.concept.Set.eq", @MethodName val domain: String = "domain", @MethodName val codomain: String = "codomain") {

  object Definition {
    fun <S, DE, RE, D, R> check(f: OpUnary<DE, RE>, value: OpTernary<D, DE, DE, DE>, codValue: OpTernary<R, RE, RE, RE>, eq: OpTernary<R, RE, RE, Boolean>, domain: OpNullary<D>, codomain: OpNullary<R>, a: DE, b: DE
    ): Boolean {
      return eq.ap(codomain.ap(), codValue.ap(codomain.ap(), f.ap(a), f.ap(b)), f.ap(value.ap(domain.ap(), a, b)))
    }
  }
}
