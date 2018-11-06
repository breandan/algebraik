package co.ndan.geometry

import co.ndan.algebra.concept.PartialOrder
import co.ndan.algebra.concept.Set
import co.ndan.algebra.concept.TotalOrder
import co.ndan.util.Pair

/*
 *
 *
 * @param <P>
 * @param <XMC>
</XMC></P> */
interface ArrangementTraits<P, XMC> {

  fun points(): TotalOrder<P>

  fun curves(): Set<XMC>

  /* points().leq(minVertex(c), maxVertex(c))  */
  fun minVertex(curve: XMC): P

  fun maxVertex(curve: XMC): P

  /*
   * Given two curves c1 and c2 having a common left endpoint p,
   * compareCurvesRightOf(p).leq(c1,c2) returns true if c1 lies on or below c2
   * immediately to the right of p.
   *
   * This order should be total when restricted to XMCs having p as a right
   * endpoint. All other curves should be unrelated.
   */
  fun compareCurvesRightOf(p: P): PartialOrder<XMC>

  /*
   * return all of the intersections between the curves c1 and c2, ordered
   * from leftmost to rightmost.  The closures of the returned components
   * should be disjoint, so for example intersect(c,c) = [c] (and not
   * [minVertex(c), c, maxVertex(c)] for example).
   */
  fun intersect(c1: XMC, c2: XMC): List<Subcurve<P, XMC>>

  /*
   * Given a point p on the curve c, split c into a pair r of curves such that
   * maxVertex(r.first) = p = minVertex(r.second).
   *
   * @throws IllegalArgumentException
   * if p does not lie on c.
   */
  @Throws(IllegalArgumentException::class)
  fun split(c: XMC, p: P): Pair<XMC, XMC>
}
