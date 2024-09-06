package ch.addere.dga.core.model.graph

import assertk.assertThat
import assertk.assertions.isEqualTo
import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module
import ch.addere.dga.core.domain.model.graph.ModuleDependencyDag
import ch.addere.dga.core.domain.service.DependencyRepository
import ch.addere.dga.core.domain.service.ModuleRepository
import org.junit.jupiter.api.Test

class ModuleDependencyDagTest {

    private val m1 = Module("m1")
    private val m2 = Module("m2")
    private val c1 = Configuration("c1")
    private val d1 = Dependency(m1, m2, c1)

    @Test
    fun `test add two different modules`() {
        val dag = dag(setOf(m1, m2), setOf())

        assertThat(dag.nofVertices()).isEqualTo(2)
        assertThat(dag.vertices().first().module).isEqualTo(m1)
        assertThat(dag.vertices().last().module).isEqualTo(m2)
        assertThat(dag.nofEdges()).isEqualTo(0)
    }

    @Test
    fun `test add identical module multiple times`() {
        val dag = dag(setOf(m1, m1, m1), setOf())

        assertThat(dag.nofVertices()).isEqualTo(1)
        assertThat(dag.vertices().first().module).isEqualTo(m1)
        assertThat(dag.nofEdges()).isEqualTo(0)
    }

    @Test
    fun `test add dependency`() {
        val dag = dag(setOf(m1, m2), setOf(d1))

        assertThat(dag.nofVertices()).isEqualTo(2)
        assertThat(dag.vertices().first().module).isEqualTo(m1)
        assertThat(dag.vertices().last().module).isEqualTo(m2)
        assertThat(dag.nofEdges()).isEqualTo(1)
        assertThat(dag.edges().first().configuration).isEqualTo(c1)
    }

    private fun dag(modules: Set<Module>, dependencies: Set<Dependency>): ModuleDependencyDag {
        val moduleRepository = ModuleRepository()
        val dependencyRepository = DependencyRepository()

        moduleRepository.addModule(modules)
        dependencyRepository.addDependency(dependencies)

        return ModuleDependencyDag(moduleRepository, dependencyRepository)
    }
}
