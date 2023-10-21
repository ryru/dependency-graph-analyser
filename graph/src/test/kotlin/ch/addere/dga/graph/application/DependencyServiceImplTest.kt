package ch.addere.dga.graph.application

import assertk.assertThat
import assertk.assertions.isEqualTo
import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.Module
import ch.addere.dga.graph.domain.service.DependencyRepository
import org.junit.jupiter.api.Test

class DependencyServiceImplTest {

    @Test
    fun `test empty repo has zero modules`() {
        val service = DependencyServiceImpl(DependencyRepository())

        assertThat(service.nofDependencies()).isEqualTo(0)
        assertThat(service.nofUniqueDependencies()).isEqualTo(0)
    }

    @Test
    fun `test repo with modules has non zero modules`() {
        val service = serviceWithModules()

        assertThat(service.nofDependencies()).isEqualTo(3)
        assertThat(service.nofUniqueDependencies()).isEqualTo(2)
    }

    private fun serviceWithModules(): DependencyService {
        val m1 = Module("m1")
        val m2 = Module("m2")
        val m3 = Module("m3")
        val c1 = Configuration("c1")
        val c2 = Configuration("c2")
        val d1 = Dependency(m3, m1, c1)
        val d2 = Dependency(m2, m1, c1)
        val d3 = Dependency(m2, m3, c2)
        val dependencyRepository = DependencyRepository()
        dependencyRepository.addDependency(listOf(d1, d2, d3))
        return DependencyServiceImpl(dependencyRepository)
    }
}
