package ch.addere.mdg.domain.model.graph

import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.hasMessage
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import ch.addere.mdg.domain.model.Configuration
import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module
import org.junit.jupiter.api.Test

class ModuleDependencyDagTest {

    private val m1 = Module("m1")
    private val m2 = Module("m2")
    private val c1 = Configuration("c1")
    private val d1 = Dependency(m1, m2, c1)

    @Test
    fun `test add two different modules`() {
        val dag = ModuleDependencyDag()

        dag.addModule(m1)
        dag.addModule(m2)

        assertThat(dag.nofModules()).isEqualTo(2)
        assertThat(dag.modules().first().module).isEqualTo(m1)
        assertThat(dag.modules().last().module).isEqualTo(m2)
        assertThat(dag.nofDependencies()).isEqualTo(0)
    }

    @Test
    fun `test add identical module multiple times`() {
        val dag = ModuleDependencyDag()

        dag.addModule(m1)
        dag.addModule(m1)
        dag.addModule(m1)

        assertThat(dag.nofModules()).isEqualTo(1)
        assertThat(dag.modules().first().module).isEqualTo(m1)
        assertThat(dag.nofDependencies()).isEqualTo(0)
    }

    @Test
    fun `test add dependency`() {
        val dag = ModuleDependencyDag()

        dag.addDependency(d1)

        assertThat(dag.nofModules()).isEqualTo(2)
        assertThat(dag.modules().first().module).isEqualTo(m1)
        assertThat(dag.modules().last().module).isEqualTo(m2)
        assertThat(dag.nofDependencies()).isEqualTo(1)
        assertThat(dag.dependencies().first().configuration).isEqualTo(c1)
    }

    @Test
    fun `test throw on add identical dependency multiple times`() {
        val dag = ModuleDependencyDag()

        dag.addDependency(d1)

        assertFailure { dag.addDependency(d1) }
            .isInstanceOf(IllegalArgumentException::class)
            .hasMessage("Dependency of type 'c1' between origin 'm1' and destination 'm2' already exists")
        assertThat(dag.nofModules()).isEqualTo(2)
        assertThat(dag.modules().first().module).isEqualTo(m1)
        assertThat(dag.modules().last().module).isEqualTo(m2)
        assertThat(dag.nofDependencies()).isEqualTo(1)
        assertThat(dag.dependencies().first().configuration).isEqualTo(c1)
    }
}
