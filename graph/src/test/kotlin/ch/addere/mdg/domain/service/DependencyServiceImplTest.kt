package ch.addere.mdg.domain.service

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEmpty
import ch.addere.mdg.domain.model.Configuration
import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module
import ch.addere.mdg.domain.model.graph.ModuleDependencyDag
import org.junit.jupiter.api.Test

class DependencyServiceImplTest {

    /**
     * Diagram of the test DAG.
     *
     *     m8     m0
     *     | \  /   \    m9
     *     |  m1     \  / |
     *     | /  \    m4   |
     *     m2   m3   |   m7
     *    /       \  |  /
     *  m6          m5
     *
     *  Direct              Non-direct              All
     *    m0: -               m0: -                   m0: -
     *    m1: m0, m8          m1: -                   m1: m0, m8
     *    m2: m1, m8          m2: m0                  m2: m0, m1, m8
     *    m3: m1              m3: m0, m8              m3: m0, m1, m8
     *    m4: m0, m9          m4: -                   m4: m0, m9
     *    m5: m3, m4, m7      m5: m0, m1, m8, m9      m5: m0, m1, m3, m4, m7, m8, m9
     *    m6: m2              m6: m0, m1, m8          m6: m0, m1, m2, m8
     *    m7: m9              m7: -                   m7: m9
     *    m8: -               m8: -                   m8: -
     *    m9: -               m9: -                   m9: -
     */

    private val m0 = Module("m0")
    private val m1 = Module("m1")
    private val m2 = Module("m2")
    private val m3 = Module("m3")
    private val m4 = Module("m4")
    private val m5 = Module("m5")
    private val m6 = Module("m6")
    private val m7 = Module("m7")
    private val m8 = Module("m8")
    private val m9 = Module("m9")
    private val c1 = Configuration("c1")
    private val c2 = Configuration("c2")
    private val d10 = Dependency(m1, m0, c1)
    private val d18 = Dependency(m1, m8, c2)
    private val d21 = Dependency(m2, m1, c1)
    private val d28 = Dependency(m2, m8, c2)
    private val d31 = Dependency(m3, m1, c1)
    private val d40 = Dependency(m4, m0, c1)
    private val d49 = Dependency(m4, m9, c2)
    private val d53 = Dependency(m5, m3, c1)
    private val d54 = Dependency(m5, m4, c1)
    private val d57 = Dependency(m5, m7, c2)
    private val d62 = Dependency(m6, m2, c2)
    private val d79 = Dependency(m7, m9, c2)

    @Test
    fun `test get all modules`() {
        val service = DependencyServiceImpl(dag())

        val modules = service.allModules()

        assertThat(modules).containsExactlyInAnyOrder(m0, m1, m2, m3, m4, m5, m6, m7, m8, m9)
    }

    @Test
    fun `test get all modules with configuration c1`() {
        val service = DependencyServiceImpl(dag())

        val modules = service.allModules(c1)

        assertThat(modules).containsExactlyInAnyOrder(m1, m2, m3, m4, m5)
    }

    @Test
    fun `test get all modules with configuration c2`() {
        val service = DependencyServiceImpl(dag())

        val modules = service.allModules(c2)

        assertThat(modules).containsExactlyInAnyOrder(m1, m2, m4, m5, m6, m7)
    }

    @Test
    fun `test get all dependencies`() {
        val service = DependencyServiceImpl(dag())

        val dependencies = service.allDependencies()

        assertThat(dependencies)
            .containsExactlyInAnyOrder(d10, d18, d21, d28, d31, d40, d49, d53, d54, d57, d62, d79)
    }

    @Test
    fun `test direct dependencies`() {
        val service = DependencyServiceImpl(dag())

        assertThat(service.directDependenciesOf(m0)).isEmpty()
        assertThat(service.directDependenciesOf(m1)).containsExactlyInAnyOrder(d10, d18)
        assertThat(service.directDependenciesOf(m2)).containsExactlyInAnyOrder(d21, d28)
        assertThat(service.directDependenciesOf(m3)).contains(d31)
        assertThat(service.directDependenciesOf(m4)).containsExactlyInAnyOrder(d40, d49)
        assertThat(service.directDependenciesOf(m5)).containsExactlyInAnyOrder(d53, d54, d57)
        assertThat(service.directDependenciesOf(m6)).contains(d62)
        assertThat(service.directDependenciesOf(m7)).contains(d79)
        assertThat(service.directDependenciesOf(m8)).isEmpty()
        assertThat(service.directDependenciesOf(m9)).isEmpty()
    }

    @Test
    fun `test non-direct dependencies`() {
        val service = DependencyServiceImpl(dag())

        assertThat(service.nonDirectDependenciesOf(m0)).isEmpty()
        assertThat(service.nonDirectDependenciesOf(m1)).isEmpty()
        assertThat(service.nonDirectDependenciesOf(m2)).contains(d10)
        assertThat(service.nonDirectDependenciesOf(m3)).containsExactlyInAnyOrder(d10, d18)
        assertThat(service.nonDirectDependenciesOf(m4)).isEmpty()
        assertThat(service.nonDirectDependenciesOf(m5))
            .containsExactlyInAnyOrder(d10, d18, d31, d40, d49, d79)
        assertThat(service.nonDirectDependenciesOf(m6))
            .containsExactlyInAnyOrder(d10, d18, d21, d28)
        assertThat(service.nonDirectDependenciesOf(m7)).isEmpty()
        assertThat(service.nonDirectDependenciesOf(m8)).isEmpty()
        assertThat(service.nonDirectDependenciesOf(m9)).isEmpty()
    }

    @Test
    fun `test all dependencies`() {
        val service = DependencyServiceImpl(dag())

        assertThat(service.allDependenciesOf(m0)).isEmpty()
        assertThat(service.allDependenciesOf(m1)).containsExactlyInAnyOrder(d10, d18)
        assertThat(service.allDependenciesOf(m2)).containsExactlyInAnyOrder(d10, d21, d28, d18)
        assertThat(service.allDependenciesOf(m3)).containsExactlyInAnyOrder(d10, d31, d18)
        assertThat(service.allDependenciesOf(m4)).containsExactlyInAnyOrder(d40, d49)
        assertThat(service.allDependenciesOf(m5))
            .containsExactlyInAnyOrder(d10, d31, d40, d53, d54, d18, d49, d79, d57)
        assertThat(service.allDependenciesOf(m6)).containsExactlyInAnyOrder(d62, d21, d28, d18, d10)
        assertThat(service.allDependenciesOf(m7)).contains(d79)
        assertThat(service.allDependenciesOf(m8)).isEmpty()
        assertThat(service.allDependenciesOf(m9)).isEmpty()
    }

    private fun dag(): ModuleDependencyDag {
        val dag = ModuleDependencyDag()
        dag.addAllModule(m3, m2, m1, m0, m3, m5, m4, m7, m6, m8, m9)
        dag.addAllDependency(d31, d21, d10, d54, d53, d40, d18, d28, d49, d57, d62, d79)
        return dag
    }
}
