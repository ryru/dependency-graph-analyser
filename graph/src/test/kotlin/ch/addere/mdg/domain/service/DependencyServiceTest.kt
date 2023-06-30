package ch.addere.mdg.domain.service

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsAll
import assertk.assertions.isEmpty
import ch.addere.mdg.domain.model.Configuration
import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module
import ch.addere.mdg.domain.model.graph.ModuleDependencyDag
import org.junit.jupiter.api.Test

class DependencyServiceTest {

    /**
     *            m1
     *          /   \
     *        m2     \
     *       /  \    m5
     *     m3   m4   /
     *           \ /
     *           m6
     */

    private val m1 = Module("m1")
    private val m2 = Module("m2")
    private val m3 = Module("m3")
    private val m4 = Module("m4")
    private val m5 = Module("m5")
    private val m6 = Module("m6")
    private val c1 = Configuration("c1")
    private val d1 = Dependency(m2, m1, c1)
    private val d2 = Dependency(m3, m2, c1)
    private val d3 = Dependency(m4, m2, c1)
    private val d4 = Dependency(m5, m1, c1)
    private val d5 = Dependency(m6, m4, c1)
    private val d6 = Dependency(m6, m5, c1)

    @Test
    fun `test get all modules`() {
        val dependencyService = DependencyService(dag())

        val allModules = dependencyService.allModules()

        assertThat(allModules).containsAll(m1, m2, m3, m4, m5, m6)
    }

    @Test
    fun `test get all dependencies`() {
        val dependencyService = DependencyService(dag())

        val allDependencies = dependencyService.allDependencies()

        assertThat(allDependencies).containsAll(d1, d2, d3, d4, d5, d6)
    }

    @Test
    fun `test direct dependencies`() {
        val dependencyService = DependencyService(dag())

        assertThat(dependencyService.directDependencies(m1)).isEmpty()
        assertThat(dependencyService.directDependencies(m2)).contains(d1)
        assertThat(dependencyService.directDependencies(m3)).contains(d2)
        assertThat(dependencyService.directDependencies(m4)).contains(d3)
        assertThat(dependencyService.directDependencies(m5)).contains(d4)
        assertThat(dependencyService.directDependencies(m6)).containsAll(d5, d6)
    }

    @Test
    fun `test non-direct dependencies`() {
        val dependencyService = DependencyService(dag())

        assertThat(dependencyService.nonDirectDependencies(m1)).isEmpty()
        assertThat(dependencyService.nonDirectDependencies(m2)).isEmpty()
        assertThat(dependencyService.nonDirectDependencies(m3)).contains(d1)
        assertThat(dependencyService.nonDirectDependencies(m4)).contains(d1)
        assertThat(dependencyService.nonDirectDependencies(m5)).isEmpty()
        assertThat(dependencyService.nonDirectDependencies(m6)).containsAll(d1, d3, d4)
    }

    @Test
    fun `test all dependencies`() {
        val dependencyService = DependencyService(dag())

        assertThat(dependencyService.dependencies(m1)).isEmpty()
        assertThat(dependencyService.dependencies(m2)).contains(d1)
        assertThat(dependencyService.dependencies(m3)).containsAll(d1, d2)
        assertThat(dependencyService.dependencies(m4)).containsAll(d1, d3)
        assertThat(dependencyService.dependencies(m5)).contains(d4)
        assertThat(dependencyService.dependencies(m6)).containsAll(d1, d3, d4, d5, d6)
    }

    private fun dag(): ModuleDependencyDag {
        val dag = ModuleDependencyDag()
        dag.addAllModule(m4, m3, m2, m1, m4)
        dag.addAllDependency(d3, d2, d1, d6, d5, d4)
        return dag
    }
}
