package co.ndan.geometry

interface Subcurve<P, XMC> {
  @Throws(E::class)
  fun <R, E : Exception> accept(visitor: SubcurveVisitor<R, P, XMC, E>): R

  interface SubcurveVisitor<R, P, XMC, E : Exception> {
    @Throws(E::class)
    fun visitPoint(p: Point<P, XMC>): R

    @Throws(E::class)
    fun visitCurve(p: Curve<P, XMC>): R
  }

  class Point<P, XMC>(val point: P, val multiplicity: Int) : Subcurve<P, XMC> {
    @Throws(E::class)
    override fun <R, E : Exception> accept(visitor: SubcurveVisitor<R, P, XMC, E>) = visitor.visitPoint(this)
  }

  class Curve<P, XMC>(val curve: XMC) : Subcurve<P, XMC> {
    @Throws(E::class)
    override fun <R, E : Exception> accept(visitor: SubcurveVisitor<R, P, XMC, E>) = visitor.visitCurve(this)
  }
}