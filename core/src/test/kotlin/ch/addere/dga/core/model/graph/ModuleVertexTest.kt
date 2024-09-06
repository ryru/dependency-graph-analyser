package ch.addere.dga.core.model.graph

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.hasMessage
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Module
import ch.addere.dga.core.domain.model.graph.DependencyEdge
import ch.addere.dga.core.domain.model.graph.ModuleVertex
import org.junit.jupiter.api.Test

class ModuleVertexTest {

    private val m1 = Module("m1")
    private val m2 = Module("m2")
    private val m3 = Module("m3")
    private val c1 = Configuration("c1")
    private val c2 = Configuration("c2")
    private val mv1 = ModuleVertex(m1)
    private val mv2 = ModuleVertex(m2)
    private val mv3 = ModuleVertex(m3)
    private val ed1 = DependencyEdge(mv1, mv2, c1)
    private val ed2 = DependencyEdge(mv1, mv3, c1)
    private val ed3 = DependencyEdge(mv1, mv2, c2)

    @Test
    fun `test simple vertex`() {
        val vertex = ModuleVertex(m1)

        assertThat(vertex.module).isEqualTo(m1)
    }

    @Test
    fun `test single outgoing dependency`() {
        val vertex = ModuleVertex(m1)

        vertex.addOutgoing(mv2, ed1)

        assertThat(vertex.module).isEqualTo(m1)
        assertThat(vertex.getOutgoing().size).isEqualTo(1)
        assertThat(vertex.getOutgoing()[mv2]!!.first().configuration).isEqualTo(c1)
    }

    @Test
    fun `test multiple outgoing dependency to different destination with identical configurations`() {
        val vertex = ModuleVertex(m1)

        vertex.addOutgoing(mv2, ed1)
        vertex.addOutgoing(mv3, ed2)

        assertThat(vertex.module).isEqualTo(m1)
        assertThat(vertex.getOutgoing().size).isEqualTo(2)
        assertThat(vertex.getOutgoing()[mv2]!!.first().configuration).isEqualTo(c1)
        assertThat(vertex.getOutgoing()[mv3]!!.first().configuration).isEqualTo(c1)
    }

    @Test
    fun `test multiple outgoing dependency to same destination with different configurations`() {
        val vertex = ModuleVertex(m1)

        vertex.addOutgoing(mv2, ed1)
        vertex.addOutgoing(mv2, ed3)

        assertThat(vertex.module).isEqualTo(m1)
        assertThat(vertex.getOutgoing().size).isEqualTo(1)
        assertThat(vertex.getOutgoing()[mv2]!!.first().configuration).isEqualTo(c1)
        assertThat(vertex.getOutgoing()[mv2]!!.last().configuration).isEqualTo(c2)
    }

    @Test
    fun `test single incoming dependency`() {
        val vertex = ModuleVertex(m1)

        vertex.addIncoming(mv2, ed1)

        assertThat(vertex.module).isEqualTo(m1)
        assertThat(vertex.getIncoming().size).isEqualTo(1)
        assertThat(vertex.getIncoming()[mv2]!!.first().configuration).isEqualTo(c1)
    }

    @Test
    fun `test multiple incoming dependency to different destination with identical configurations`() {
        val vertex = ModuleVertex(m1)

        vertex.addIncoming(mv2, ed1)
        vertex.addIncoming(mv3, ed2)

        assertThat(vertex.module).isEqualTo(m1)
        assertThat(vertex.getIncoming().size).isEqualTo(2)
        assertThat(vertex.getIncoming()[mv2]!!.first().configuration).isEqualTo(c1)
        assertThat(vertex.getIncoming()[mv3]!!.first().configuration).isEqualTo(c1)
    }

    @Test
    fun `test multiple incoming dependency to same destination with different configurations`() {
        val vertex = ModuleVertex(m1)

        vertex.addIncoming(mv2, ed1)
        vertex.addIncoming(mv2, ed3)

        assertThat(vertex.module).isEqualTo(m1)
        assertThat(vertex.getIncoming().size).isEqualTo(1)
        assertThat(vertex.getIncoming()[mv2]!!.first().configuration).isEqualTo(c1)
        assertThat(vertex.getIncoming()[mv2]!!.last().configuration).isEqualTo(c2)
    }

    @Test
    fun `test throw multiple incoming dependency to same destination with different configurations`() {
        val vertex = ModuleVertex(m1)

        vertex.addIncoming(mv2, ed1)

        assertFailure { vertex.addIncoming(mv2, ed1) }
            .isInstanceOf(IllegalArgumentException::class)
            .hasMessage("Dependency of module 'm2' and configuration 'c1' already exists")
        assertThat(vertex.module).isEqualTo(m1)
        assertThat(vertex.getIncoming().size).isEqualTo(1)
        assertThat(vertex.getIncoming()[mv2]!!.size).isEqualTo(1)
        assertThat(vertex.getIncoming()[mv2]!!.first().configuration).isEqualTo(c1)
    }
}
