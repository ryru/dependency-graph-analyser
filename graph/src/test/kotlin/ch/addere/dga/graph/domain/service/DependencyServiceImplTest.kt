package ch.addere.dga.graph.domain.service

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import ch.addere.dga.graph.application.DependencyService
import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.Module
import org.junit.jupiter.api.Test

class DependencyServiceImplTest {

    private val m1 = Module("m1")
    private val m2 = Module("m2")
    private val m3 = Module("m3")
    private val c1 = Configuration("c1")
    private val c2 = Configuration("c2")
    private val d1 = Dependency(m3, m1, c1)
    private val d2 = Dependency(m2, m1, c1)
    private val d3 = Dependency(m2, m3, c2)

    @Test
    fun `test empty repo has zero modules`() {
        val service = DependencyServiceImpl(DependencyRepository())

        assertThat(service.nofDependencies()).isEqualTo(0)
        assertThat(service.nofUniqueDependencies()).isEqualTo(0)
        assertThat(service.configurationsWithOccurrence()).isEmpty()
    }

    @Test
    fun `test repo with modules has non zero modules`() {
        val service = serviceWithModules()

        assertThat(service.nofDependencies()).isEqualTo(3)
        assertThat(service.nofUniqueDependencies()).isEqualTo(2)
        assertThat(service.configurationsWithOccurrence()).transform { it[c1] == 2 }
        assertThat(service.configurationsWithOccurrence()).transform { it[c2] == 1 }
    }

    private fun serviceWithModules(): DependencyService {
        val dependencyRepository = DependencyRepository()
        dependencyRepository.addDependency(listOf(d1, d2, d3))
        return DependencyServiceImpl(dependencyRepository)
    }
}
