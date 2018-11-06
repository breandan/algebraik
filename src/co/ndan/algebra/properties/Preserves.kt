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
annotation class Preserves(@MethodExt val value: String, @MethodDup val codValue: String = "value", @MethodName val domain: String = "domain", @MethodName val codomain: String = "codomain") {
  object Definition {
    fun <DE, D, CE, C, R> check(m: OpUnary<DE, CE>, value: OpTernary<D, DE, DE, Boolean>, codValue: OpTernary<C, CE, CE, Boolean>, domain: OpNullary<D>, codomain: OpNullary<C>, a: DE, b: DE): Boolean = if (value.ap(domain.ap(), a, b)) codValue.ap(codomain.ap(), m.ap(a), m.ap(b)) else true
  }
}
