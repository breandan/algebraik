package co.ndan.geometry

import java.util.Collections


import co.ndan.algebra.adapters.OrderedFieldUtils
import co.ndan.algebra.concept.OrderedField
import co.ndan.algebra.concept.PartialOrder
import co.ndan.algebra.concept.Set
import co.ndan.algebra.concept.TotalOrder
import co.ndan.util.Pair

class SegmentTraits<NT>(f: OrderedField<NT>, private val points: TotalOrder<Point> = object : TotalOrder<Point> {
  override fun eq(a: Point, b: Point): Boolean {
    return f.eq(a.x, b.x)!! && f.eq(a.y, b.y)!!
  }

  override fun leq(a: Point, b: Point): Boolean =
    f.leq(a.x, b.x)!! || f.eq(a.x, b.x)!! && f.leq(a.y, b.y)!!
}) : ArrangementTraits<SegmentTraits.Point, SegmentTraits.Segment> {
  private val f: OrderedFieldUtils<NT>

  private val curves = Set<Segment> { a, b -> points.eq(a.min, b.min) && points.eq(a.max, b.max) }

  init {
    this.f = OrderedFieldUtils(f)
  }

  /*
	 * points ******************************************************************
	 */

  internal inner class Point(val x: NT, val y: NT)

  override fun points(): TotalOrder<Point> {
    return points
  }

  /*
	 * Lines *******************************************************************
	 */

  internal inner class Segment(a: Point, b: Point) {
    val min: Point
    val max: Point

    init {
      val aless = points.leq(a, b)
      this.min = if (aless) a else b
      this.max = if (aless) b else a
    }
  }

  override fun curves() = curves

  override fun minVertex(curve: Segment) = curve.min

  override fun maxVertex(curve: Segment) = curve.max

  /*
	 * Geometry ****************************************************************
	 */

  override fun compareCurvesRightOf(p: Point): PartialOrder<Segment> {
    return object : PartialOrder<Segment> {
      override fun eq(a: Segment, b: Segment) = leq(a, b) && leq(b, a)

      override fun leq(a: Segment, b: Segment): Boolean {
        if (!points.eq(a.min, p) || !points.eq(b.min, p))
          throw IllegalArgumentException()

        // p1 = a.min - p; p2 = b.min - p;
        val p1x = f.minus(a.min.x, p.x)
        val p1y = f.minus(a.min.y, p.y)
        val p2x = f.minus(b.min.x, p.x)
        val p2y = f.minus(b.min.y, p.y)

        return f.divEq(p1y, p1x, p2y, p2x)
      }
    }
  }

  override fun intersect(c1: Segment, c2: Segment): List<Subcurve<Point, Segment>> {
    var c1 = c1
    var c2 = c2
    //
    // Let pi = ci.max, qi = ci.min
    //

    val p1x = c1.max.x
    val p1y = c1.max.y
    val q1x = c1.min.x
    val q1y = c1.min.y

    val p2x = c2.max.x
    val p2y = c2.max.y
    val q2x = c2.min.x
    val q2y = c2.min.y

    //
    // Then we wish to find a point p such that
    //    p = t1*p1 + (1-t1)*q1
    // and also
    //    p = t2*p2 + (1-t2)*q2
    // for some t1 and t2 between 0 and 1.
    //
    // We can write this as a matrix equation
    //
    // ⎛                      ⎞   ⎛   ⎞   ⎛           ⎞
    // ⎜ (p1-q1).x  (p2-q2).x ⎟   ⎜ t1⎟   ⎜ (q2-q1).x ⎟
    // ⎜                      ⎟ * ⎜   ⎟ = ⎜           ⎟
    // ⎜ (p1-q1).y  (p2-q2).y ⎟   ⎜-t2⎟   ⎜ (q2-q1).y ⎟
    // ⎝                      ⎠   ⎝   ⎠   ⎝           ⎠
    //
    // which we will write
    //
    // ⎛          ⎞   ⎛   ⎞   ⎛     ⎞
    // ⎜ d1x  d2x ⎟   ⎜ t1⎟   ⎜ dqx ⎟
    // ⎜          ⎟ * ⎜   ⎟ = ⎜     ⎟
    // ⎜ d1y  d2y ⎟   ⎜-t2⎟   ⎜ dqy ⎟
    // ⎝          ⎠   ⎝   ⎠   ⎝     ⎠
    //
    // by defining

    val d1x = f.minus(p1x, q1x)
    val d1y = f.minus(p1y, q1y)
    val d2x = f.minus(p2x, q2x)
    val d2y = f.minus(p2y, q2y)
    val dqx = f.minus(q2x, q1x)
    val dqy = f.minus(q2y, q1y)

    // If there is a solution, then it satisfies
    //
    //   ⎛    ⎞   ⎛           ⎞ ⎛     ⎞
    //   ⎜ t1 ⎟   ⎜ d2y  -d1y ⎟ ⎜ dqx ⎟
    // D ⎜    ⎟ = ⎜           ⎟ ⎜     ⎟
    //   ⎜-t2 ⎟   ⎜-d2x   d1x ⎟ ⎜ dqy ⎟
    //   ⎝    ⎠   ⎝           ⎠ ⎝     ⎠
    //
    // where D = d1x * d2y - d1y * d2x is the determinant of the
    // matrix on the lhs.

    val D = f.minus(f.times(d1x, d2y), f.times(d1y, d2x))

    //
    // If D = 0, then the two lines are parallel (D = 0 exactly when
    // dy1/dx1 = dy2/dx2)
    //

    if (f.eq(D, f.zero())!!) {
      //
      // In this case, the lines may either coincide or be
      // disjoint.
      //
      // To figure out which, we will see if P2 lies on the line defined
      // by C1.  This will be true if the line from Q1 to Q2 has the same
      // slope as the line from P1 to Q1.
      //

      if (!f.divEq(dqy, dqx, d1y, d1x)) {
        // In this case, c1 and c2 are disjoint.  Return no intersections.
        return emptyList<Subcurve<Point, Segment>>()
      } else {
        //
        // In this case, c1 and c2 are collinear.  So, we need to figure
        // out the overlap
        //
        // Let's reorder the two curves so that a1 is smaller than a2.

        if (points.leq(c1.min, c2.min)) {
          val t = c1
          c1 = c2
          c2 = t
        }

        // Now there are a few possible cases, depending on the location
        // of c1.max relative to the endpoints of c2:

        if (points.leq(c2.max, c1.max))
        // c1: min ------------- max
        // c2:      min --- max
        // ix: c2
          return listOf<Subcurve<Point, Segment>>(Subcurve.Curve<Point, Segment>(c2) as Subcurve<Point, Segment>)

        if (points.eq(c2.min, c1.max))
        // c1: min ----- max
        // c2:           min ----- max
        // ix: c1.max
          return listOf<Subcurve<Point, Segment>>(Subcurve.Point<Point, Segment>(c1.max, 1) as Subcurve<Point, Segment>)

        return if (points.leq(c2.min, c1.max))
        // c1: min ----- max
        // c2:      min ----- max
        // ix: (c2.min, c1.max)

          listOf<Subcurve<Point, Segment>>(Subcurve.Curve<Point, Segment>(
            Segment(c2.min, c1.max)) as Subcurve<Point, Segment>)
        else
        // c1: min --- max
        // c2:             min --- max
        // ix: none
          emptyList<Subcurve<Point, Segment>>()
      }
    } else {
      //
      // If the two lines are not parallel, they intersect at
      // exactly the point p, defined above as
      //    p = t1*p1 + (1-t1)*q1 = t2*p2 + (1-t2)*q2
      //
      // We first solve for t1 and t2.
      //

      val rhs1 = f.minus(f.times(d2y, dqx), f.times(d1y, dqy))
      val rhs2 = f.minus(f.times(d1x, dqy), f.times(d2x, dqx))

      val C = f.inv(D)

      val t1 = f.times(C, rhs1)
      val t2 = f.neg(f.times(C, rhs2))

      // We then determine whether the point lies within the line segments.
      // This will be true if and only if both t1 and t2 are between 0 and
      // 1.

      if (f.leq(f.zero(), t1)!! && f.leq(t1, f.one())!! &&
        f.leq(f.zero(), t2)!! && f.leq(t2, f.one())!!) {
        // we have an intersection: compute p and return it.
        val px = f.plus(f.times(t1, p1x),
          f.times(f.minus(f.one(), t1), q1x))
        val py = f.plus(f.times(t1, p1y),
          f.times(f.minus(f.one(), t1), q1y))

        val p = Point(px, py)
        val result = Subcurve.Point<Point, Segment>(p, 1)

        return listOf<Subcurve<Point, Segment>>(result)
      } else {
        // no intersection
        return emptyList<Subcurve<Point, Segment>>()
      }
    }
  }

  @Throws(IllegalArgumentException::class)
  override fun split(c: Segment, p: Point): Pair<Segment, Segment> {
    //
    // to satisfy the contract, we must check that p lies on c.
    //

    if (!intersect(p, c))
      throw IllegalArgumentException("p is not on c")

    return Pair(Segment(c.min, p), Segment(p, c.max)
    )
  }

  private fun intersect(p: Point, c: Segment): Boolean {
    if (points.eq(p, c.min) || points.eq(p, c.max))
      return true

    if (points.leq(p, c.min) || points.leq(c.max, p))
      return false

    //
    // compare the slopes of
    //     (c.min, p) = dy1/dx1
    // and
    //     (c.min, c.max) = dy2/dx2
    //

    val dy1 = f.minus(p.y, c.min.y)
    val dx1 = f.minus(p.x, c.min.x)

    val dy2 = f.minus(c.max.y, c.min.y)
    val dx2 = f.minus(c.max.x, c.min.x)

    return f.divEq(dy1, dx1, dy2, dx2)
  }
}
