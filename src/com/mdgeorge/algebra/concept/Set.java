package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.*;


/**
 * Set<E> is the root of the mathematical structure hierarchy.  Classes that
 * implement Set<E> should be singleton types that represent a single set.  For
 * example, the set of integers has a corresponding singleton type (Z) which
 * defines the set, group, and ring operations.
 * 
 * Methods in Set and its subinterfaces define operations over the kinds of sets
 * they represent; they should contain annotations defining the properties which
 * those operations must satisfy.
 *
 * @see <a href="http://en.wikipedia.org/wiki/Set_%28mathematics%29">Set on Wikipedia</a>
 * @author mdgeorge
 */
public interface Set<E> {
	@Reflexive @Transitive @Symmetric
	Boolean eq (E a, E b);
}
