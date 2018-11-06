package co.ndan.algebra.construction

import co.ndan.algebra.properties.meta.annotation.MagicCheck

@MagicCheck
object Q : FieldOfFractions<Int, Z>(Z)
