package ch.addere.dga.graph.domain.service

import assertk.assertThat
import assertk.assertions.containsAll
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.FilteredConfiguration
import ch.addere.dga.graph.domain.model.FilteredModules
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
    private val d4 = Dependency(m2, m2, c2)

    @Test
    fun `test empty repo has zero modules`() {
        val service = DependencyServiceImpl(DependencyRepository())

        assertThat(service.nofProjectDependencies()).isEqualTo(0)
        assertThat(service.nofUniqueConfigurations()).isEqualTo(0)
    }

    @Test
    fun `test repo with modules has non zero modules`() {
        val service = serviceWithModules()

        assertThat(service.nofProjectDependencies()).isEqualTo(4)
        assertThat(service.nofUniqueConfigurations()).isEqualTo(2)
    }

    @Test
    fun `test no applicable filters should not filter anything`() {
        val service = serviceWithModules()

        val result =
            service.filteredDependencies(
                notApplicableFilteredModules(),
                notApplicableFilteredModules(),
                notApplicableFilteredModules(),
                notApplicableFilteredConfigurations()
            )

        assertThat(result).containsAll(d1, d2, d3, d4)
    }

    @Test
    fun `test all filters set should filter all dependencies`() {
        val service = serviceWithModules()

        val result =
            service.filteredDependencies(
                filteredModules(m3),
                filteredModules(m2),
                filteredModules(m1),
                filteredConfigurations(c1)
            )

        assertThat(result).isEmpty()
    }

    @Test
    fun `test filter module should filter`() {
        val service = serviceWithModules()

        val result =
            service.filteredDependencies(
                filteredModules(m2),
                notApplicableFilteredModules(),
                notApplicableFilteredModules(),
                notApplicableFilteredConfigurations()
            )

        assertThat(result).containsAll(d2, d3, d4)
    }

    @Test
    fun `test filter module and configuration should filter`() {
        val service = serviceWithModules()

        val result =
            service.filteredDependencies(
                filteredModules(m2),
                notApplicableFilteredModules(),
                notApplicableFilteredModules(),
                filteredConfigurations(c1)
            )

        assertThat(result).containsAll(d2)
    }

    @Test
    fun `test filter origin and destination should filter`() {
        val service = serviceWithModules()

        val result =
            service.filteredDependencies(
                notApplicableFilteredModules(),
                filteredModules(m2),
                filteredModules(m1),
                notApplicableFilteredConfigurations()
            )

        assertThat(result).containsAll(d2)
    }

    private fun serviceWithModules(): DependencyService {
        val dependencyRepository = DependencyRepository()
        dependencyRepository.addDependency(listOf(d1, d2, d3, d4))
        return DependencyServiceImpl(dependencyRepository)
    }

    private fun notApplicableFilteredModules(): FilteredModules {
        return FilteredModules(false, emptyList())
    }

    private fun notApplicableFilteredConfigurations(): FilteredConfiguration {
        return FilteredConfiguration(false, emptyList())
    }

    private fun filteredModules(vararg module: Module): FilteredModules {
        return FilteredModules(true, module.toList())
    }

    private fun filteredConfigurations(vararg configurations: Configuration): FilteredConfiguration {
        return FilteredConfiguration(true, configurations.toList())
    }
}
