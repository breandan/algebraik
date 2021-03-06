package com.mdgeorge.algebra.concept;

import com.mdgeorge.algebra.properties.*;

/**
 * A (unital, commutative) ring is an abelian group with an additional
 * operation ⋅ that is associative, commutative, has an identity (1), and
 * distributes over plus.
 * 
 * @see <a href="http://en.wikipedia.org/wiki/Commutative_ring">Commutative Ring on Wikipedia</a>
 * @author mdgeorge
 */
public interface Ring  <E>
         extends Group <E>
{
	@Associative @Commutative @Identity("one") @DistributesOver("plus")
	E times (E a, E b);
	
	E one();
	
	default E fromInt(int i) {
		if (i < 0)
			return neg(fromInt(-i));
		if (i == 0)
			return zero();

		E rest = fromInt(i/2);
		return plus(plus(rest, rest), i%2 == 0 ? zero() : one());
	}
}
