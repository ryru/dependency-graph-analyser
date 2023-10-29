package ch.addere.dga.graph.application

import assertk.assertThat
import assertk.assertions.isEqualTo
import ch.addere.dga.graph.domain.model.Module
import ch.addere.dga.graph.domain.service.ModuleRepository
import org.junit.jupiter.api.Test

private val M1 = Module("m1")
private val M2 = Module("m2")

class ModuleServiceImplTest {

    @Test
    fun `test empty repo has zero modules`() {
        val service = ModuleServiceImpl(ModuleRepository())

        assertThat(service.nofModules()).isEqualTo(0)
    }

    @Test
    fun `test repo with modules has non zero modules`() {
        val service = serviceWithModules()

        assertThat(service.nofModules()).isEqualTo(2)
    }

    private fun serviceWithModules(): ModuleService {
        val moduleRepository = ModuleRepository()
        moduleRepository.addModule(listOf(M1, M2))
        return ModuleServiceImpl(moduleRepository)
    }
}
