package co.ndan.geometry

import java.util.ArrayList
import java.util.NoSuchElementException

import co.ndan.geometry.util.AbstractForwardCirculator
import co.ndan.util.NotImplementedException

class Arrangement internal constructor() {


  /*
	 ** Arrangement ************************************************************
	 */

  internal var faces: MutableCollection<Face>
  internal var vertices: Collection<Vertex>
  internal var halfedges: Collection<Halfedge>

  /*
	 ** Face type **************************************************************
	 */

  internal class Face {
    val boundaries: List<FaceBoundary>

    init {
      this.boundaries = ArrayList()
    }
  }

  internal interface FaceBoundary {
    @Throws(E::class)
    fun <R, E : Exception> accept(visitor: FaceBoundaryVisitor<E, R>): R
  }

  internal interface FaceBoundaryVisitor<E : Exception, R> {
    @Throws(E::class)
    fun visitHalfedge(he: Halfedge): R

    @Throws(E::class)
    fun visitIsolated(iv: IsolatedVertex): R
  }

  /*
	 ** Vertex type ************************************************************
	 */

  internal class Vertex {
    var leaving: Halfedge? = null
  }

  internal class IsolatedVertex : FaceBoundary {
    var face: Face? = null

    @Throws(E::class)
    override fun <R, E : Exception> accept(visitor: FaceBoundaryVisitor<E, R>): R {
      return visitor.visitIsolated(this)
    }
  }

  /*
	 ** Halfedge type **********************************************************
	 */

  internal class Halfedge : FaceBoundary {
    var origin: Vertex? = null
    var next: Halfedge? = null
    var twin: Halfedge? = null
    var face: Face? = null

    @Throws(E::class)
    override fun <R, E : Exception> accept(visitor: FaceBoundaryVisitor<E, R>): R {
      return visitor.visitHalfedge(this)
    }
  }

  init {
    this.faces = ArrayList()
    this.vertices = ArrayList()
    this.halfedges = ArrayList()

    this.faces.add(Face())
  }

  /*
	 ** Traversals *************************************************************
	 */

  inner class HalfedgesLeavingVertex @Throws(NoSuchElementException::class)
  constructor(source: Vertex) : AbstractForwardCirculator<Halfedge>(source.leaving) {
    internal var current: Halfedge? = null

    override fun inc() {
      current = current!!.twin!!.next
    }
  }

  inner class HalfedgesEnteringVertex @Throws(NoSuchElementException::class)
  constructor(target: Vertex) : AbstractForwardCirculator<Halfedge>(target.leaving) {

    override fun inc() {
      this.current = current.next!!.twin
    }
  }

  inner class HalfedgesAroundFace @Throws(NoSuchElementException::class)
  protected constructor(first: Halfedge) : AbstractForwardCirculator<Halfedge>(first) {

    override fun inc() {
      this.current = current.next
    }
  }

  companion object {
    fun overlay(a: Arrangement, b: Arrangement) =
      throw NotImplementedException("Arrangement.overlay")
  }
}
