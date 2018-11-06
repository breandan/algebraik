package co.ndan.algebra.properties

import java.lang.annotation.Documented

import co.ndan.algebra.properties.meta.annotation.MagicProperty
import co.ndan.algebra.properties.meta.annotation.MethodName
import co.ndan.algebra.properties.meta.annotation.MethodRef
import co.ndan.util.OpBinary
import co.ndan.util.OpNullary
import co.ndan.util.OpUnary

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Documented
@MagicProperty
annotation class Inverse(@MethodName val value: String, @MethodName val eq: String = "eq", @MethodRef val id: String = "co.ndan.algebra.properties.Identity") {

  object Definition {
    fun <E> check(m: OpBinary<E, E, E>, eq: OpBinary<E, E, Boolean>, value: OpUnary<E, E>, id: OpNullary<E>, a: E
    ): Boolean {
      return eq.ap(id.ap(), m.ap(a, value.ap(a))) && eq.ap(id.ap(), m.ap(value.ap(a), a))
    }
  }
}
