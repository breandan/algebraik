package com.mdgeorge.geometry;

import java.util.Collections;
import java.util.List;


import com.mdgeorge.algebra.adapters.OrderedFieldUtils;
import com.mdgeorge.algebra.concept.OrderedField;
import com.mdgeorge.algebra.concept.PartialOrder;
import com.mdgeorge.algebra.concept.Set;
import com.mdgeorge.algebra.concept.TotalOrder;
import com.mdgeorge.util.Pair;

public class SegmentTraits<NT>
  implements ArrangementTraits< SegmentTraits<NT>.Point
                              , SegmentTraits<NT>.Segment
                              >
{
	private final OrderedFieldUtils<NT> f;
	
	public SegmentTraits(OrderedField<NT> f)
	{
		this.f = new OrderedFieldUtils<NT> (f);
	}

	/*
	 * points ******************************************************************
	 */
	
	class Point {
		public final NT x;
		public final NT y;
		
		public Point(NT x, NT y) {
			this.x = x;
			this.y = y;
		}
	}
	
	private final TotalOrder<Point> points = new TotalOrder<Point> () {
		@Override
		public Boolean eq(Point a, Point b) {
			return f.eq(a.x, b.x) && f.eq(a.y, b.y);
		}

		@Override
		public Boolean leq(Point a, Point b) {
			return f.leq(a.x, b.x) || f.eq(a.x, b.x) && f.leq(a.y, b.y);
		}
	};
	
	@Override
	public TotalOrder<Point> points() {
		return points;
	}

	/*
	 * Lines *******************************************************************
	 */
	
	class Segment {
		public final Point min;
		public final Point max;
		
		public Segment(Point a, Point b) {
			boolean aless = points.leq(a, b);
			this.min = aless ? a : b;
			this.max = aless ? b : a;
		}
	}

	private final Set<Segment> curves = new Set<Segment> () {
		@Override
		public Boolean eq(Segment a, Segment b) {
			return points.eq(a.min, b.min) && points.eq(a.max, b.max);
		}
	};
	
	@Override
	public Set<Segment> curves() {
		return curves;
	}

	@Override
	public Point minVertex(Segment curve) {
		return curve.min;
	}

	@Override
	public Point maxVertex(Segment curve) {
		return curve.max;
	}

	/*
	 * Geometry ****************************************************************
	 */

	@Override
	public PartialOrder<Segment> compareCurvesRightOf(final Point p) {
		return new PartialOrder<Segment> () {
			@Override
			public Boolean eq(Segment a, Segment b) {
				return leq(a,b) && leq(b,a);
			}

			@Override
			public Boolean leq(Segment a, Segment b) {
				if (!points.eq(a.min, p) || !points.eq(b.min, p))
					throw new IllegalArgumentException();
				
				// p1 = a.min - p; p2 = b.min - p;
				NT p1x = f.minus(a.min.x, p.x);
				NT p1y = f.minus(a.min.y, p.y);
				NT p2x = f.minus(b.min.x, p.x);
				NT p2y = f.minus(b.min.y, p.y);

				return f.divEq(p1y, p1x, p2y, p2x);
			}
		};
	}

	@Override
	public List<Subcurve<Point, Segment>> intersect(Segment c1, Segment c2) {
		//
		// Let pi = ci.max, qi = ci.min
		//
		
		NT p1x = c1.max.x, p1y = c1.max.y;
		NT q1x = c1.min.x, q1y = c1.min.y;

		NT p2x = c2.max.x, p2y = c2.max.y;
		NT q2x = c2.min.x, q2y = c2.min.y;

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

		NT d1x = f.minus(p1x, q1x), d1y = f.minus(p1y, q1y);
		NT d2x = f.minus(p2x, q2x), d2y = f.minus(p2y, q2y);
		NT dqx = f.minus(q2x, q1x), dqy = f.minus(q2y, q1y);

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

		NT D = f.minus(f.times(d1x, d2y), f.times(d1y, d2x));

		//
		// If D = 0, then the two lines are parallel (D = 0 exactly when
		// dy1/dx1 = dy2/dx2)
		//

		if (f.eq(D, f.zero()))
		{
			//
			// In this case, the lines may either coincide or be
			// disjoint.
			//
			// To figure out which, we will see if P2 lies on the line defined
			// by C1.  This will be true if the line from Q1 to Q2 has the same
			// slope as the line from P1 to Q1.
			//

			if (!f.divEq(dqy, dqx, d1y, d1x))
			{
				// In this case, c1 and c2 are disjoint.  Return no intersections.
				return Collections.emptyList();
			}
			else
			{
				//
				// In this case, c1 and c2 are collinear.  So, we need to figure
				// out the overlap
				//
				// Let's reorder the two curves so that a1 is smaller than a2.
				
				if (points.leq(c1.min, c2.min)) {
					Segment t = c1;
					c1 = c2;
					c2 = t;
				}
				
				// Now there are a few possible cases, depending on the location
				// of c1.max relative to the endpoints of c2:
				
				if (points.leq(c2.max, c1.max))
					// c1: min ------------- max
					// c2:      min --- max
					// ix: c2
					return Collections.singletonList((Subcurve<Point,Segment>)
							new Subcurve.Curve<Point, Segment>(c2));

				if (points.eq(c2.min, c1.max))
					// c1: min ----- max
					// c2:           min ----- max
					// ix: c1.max
					return Collections.singletonList((Subcurve<Point,Segment>)
							new Subcurve.Point<Point, Segment>(c1.max, 1));
				
				if (points.leq(c2.min, c1.max))
					// c1: min ----- max
					// c2:      min ----- max
					// ix: (c2.min, c1.max)
					
					return Collections.singletonList((Subcurve<Point,Segment>)
							new Subcurve.Curve<Point, Segment> (
									new Segment(c2.min, c1.max)));
					
				else
					// c1: min --- max
					// c2:             min --- max
					// ix: none
					return Collections.emptyList();
			}
		}
		
		else
		{
			//
			// If the two lines are not parallel, they intersect at
			// exactly the point p, defined above as
			//    p = t1*p1 + (1-t1)*q1 = t2*p2 + (1-t2)*q2
			//
			// We first solve for t1 and t2.
			//

			NT rhs1 = f.minus(f.times(d2y,dqx), f.times(d1y, dqy));
			NT rhs2 = f.minus(f.times(d1x,dqy), f.times(d2x, dqx));

			NT C = f.inv(D);
			
			NT t1 =       f.times(C, rhs1);
			NT t2 = f.neg(f.times(C, rhs2));

			// We then determine whether the point lies within the line segments.
			// This will be true if and only if both t1 and t2 are between 0 and
			// 1.
			
			if (f.leq(f.zero(), t1) && f.leq(t1, f.one()) &&
			    f.leq(f.zero(), t2) && f.leq(t2, f.one()))
			{
				// we have an intersection: compute p and return it.
				NT px = f.plus(f.times(t1, p1x),
						       f.times(f.minus(f.one(), t1), q1x));
				NT py = f.plus(f.times(t1, p1y),
				               f.times(f.minus(f.one(), t1), q1y));
				
				Point p = new Point(px, py);
				Subcurve<Point, Segment> result =
						new Subcurve.Point<Point, Segment>(p, 1);
				
				return Collections.singletonList(result);
			}
			else
			{
				// no intersection
				return Collections.emptyList();
			}
		}
	}

	@Override
	public Pair<Segment, Segment> split(Segment c, Point p)
	throws IllegalArgumentException
	{
		//
		// to satisfy the contract, we must check that p lies on c.
		//
		
		if (!intersect(p, c))
			throw new IllegalArgumentException("p is not on c");
		
		return new Pair<Segment, Segment> ( new Segment(c.min, p)
		                                  , new Segment(p, c.max)
		                                  );
	}
	
	private boolean intersect(Point p, Segment c)
	{
		if (points.eq(p, c.min)  || points.eq(p, c.max))
			return true;
		
		if (points.leq(p, c.min) || points.leq(c.max, p))
			return false;
		
		//
		// compare the slopes of
		//     (c.min, p) = dy1/dx1
		// and
		//     (c.min, c.max) = dy2/dx2
		//
		
		NT dy1 = f.minus(p.y,     c.min.y);
		NT dx1 = f.minus(p.x,     c.min.x);
		
		NT dy2 = f.minus(c.max.y, c.min.y);
		NT dx2 = f.minus(c.max.x, c.min.x);
		
		if (f.divEq(dy1, dx1, dy2, dx2))
			return true;
		
		else
			return false;
	}
}
