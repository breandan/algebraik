/**
 * This package provides interfaces for working with number types that only
 * support some of the usual operations (such as plus and times). 
 * 
 * The design of this package is intended to closely mimic the mathematical
 * definitions of various algebraic structures.
 *
 * <h2>Operations on Sets, not Elements</h2>
 *
 * The first design choice that characterizes this package is that all of the
 * structure is assumed to reside in an object corresponding to the sets
 * themselves, rather than the elements of those sets.  For example, most
 * algebra packages would define an interface GroupElement with a method
 * GroupElement GroupElement.plus(GroupElement other).
 * 
 * In contrast, we define a method E {@link Group}<E>.plus(E a, E b). 
 * There are a few reasons for this choice:
 * <ul>
 *   <li>It more closely matches the mathematical definitions</li>
 *   <li>It elucidates the natural symmetry in these operations and facilitates
 *       automatic testing (see below).</li>
 *   <li>It allows defining different Group structures on the same objects.</li>
 *   <li>It makes the choice of operation explicit.  Is Integer.plus() addition
 *       modulo n?  The choice of Z.plus or Z7.plus makes this clear.</li>
 *   <li>It allows the use of objects of existing types without wrapping or
 *       subclassing.  For example, the elements of the group of Integers ({@link Z})
 *       are just java.lang.Integers.</li>
 *   <li>It avoids introducing a common supertype for elements of different
 *       groups which are really unrelated.</li>
 *   </ul>
 *   
 * This is similar to the use of {@link java.util.Comparator} objects in place
 * of the {@link java.lang.Comparable} interface.  In fact, the Comparator
 * interface is duplicated here in the {@link TotalOrder} interface, which
 * improves on Comparator in various ways.
 * 
 * <h2>Machine checkable properties</h2>
 * 
 * The differences between TotalOrder and Comparator demonstrate another key
 * feature of the design of this package.  The javadoc for the Comparator
 * interface lists certain requirements that Comparator objects should satisfy,
 * but there is no easy way to check those properties.
 * 
 * In contrast, TotalOrder.leq has java annotations that define these properties:
 * it is annotated @Total (meaning either leq(a,b) or leq(b,a)), and it inherits
 * the requirements @Reflexive, @Transitive, and @AntiSymmetric from its parent
 * interface PartialOrder.
 * 
 * These properties are accompanied by Definitions (in the
 * {@link com.mdgeorge.algebra.properties} package) that can be automatically
 * checked using the MagicProperties infrastructure.  An implementation of any
 * of these interfaces can have randomized tests automatically generated by
 * simply including the @MagicCheck annotation. 
 * 
 */
package com.mdgeorge.algebra.concept;