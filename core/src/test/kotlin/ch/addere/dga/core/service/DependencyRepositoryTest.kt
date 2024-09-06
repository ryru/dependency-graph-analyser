package ch.addere.dga.core.service

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEmpty
import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module
import ch.addere.dga.core.domain.service.DependencyRepository
import org.junit.jupiter.api.Test

private val M1 = Module("m1")
private val M2 = Module("m2")
private val M3 = Module("m3")
private val C1 = Configuration("c1")
private val C2 = Configuration("c2")
private val D1 = Dependency(M1, M2, C1)
private val D2 = Dependency(M1, M3, C1)
private val D3 = Dependency(M2, M3, C2)

class DependencyRepositoryTest {
    @Test
    fun `test empty repo has 0 dependencies`() {
        val repository = DependencyRepository()

        assertThat(repository.getAllDependencies()).isEmpty()
    }

    @Test
    fun `test add and get dependencies`() {
        val repository = DependencyRepository()

        repository.addDependency(D1)

        assertThat(repository.getAllDependencies()).contains(D1)
        assertThat(repository.getDependencyByOrigin(M1)).contains(D1)
        assertThat(repository.getDependencyByDestination(M2)).contains(D1)
        assertThat(repository.getDependencyByConfiguration(C1)).contains(D1)
    }


    @Test
    fun `test add several and get all dependencies`() {
        val repository = DependencyRepository()

        repository.addDependency(setOf(D1, D2, D3))

        assertThat(repository.getAllDependencies()).containsExactlyInAnyOrder(D1, D2, D3)
        assertThat(repository.getDependencyByOrigin(M1)).containsExactlyInAnyOrder(D1, D2)
        assertThat(repository.getDependencyByDestination(M2)).contains(D1)
        assertThat(repository.getDependencyByConfiguration(C1)).containsExactlyInAnyOrder(D1, D2)
    }
}
