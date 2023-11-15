package ch.addere.dga.graph.application

import assertk.assertThat
import assertk.assertions.containsAll
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.containsOnly
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import ch.addere.dga.graph.domain.model.Module
import ch.addere.dga.graph.domain.service.ModuleRepository
import ch.addere.dga.graph.domain.service.ModuleService
import ch.addere.dga.graph.domain.service.ModuleServiceImpl
import org.junit.jupiter.api.Test

class ModuleServiceImplTest {

    private val mApp = Module("app")
    private val mLibA = Module("libA")
    private val mLibB = Module("libB")
    private val mLibC = Module("libC")
    private val mDependencyAOther = Module("dependency-a-other")
    private val mDependencyBOther = Module("dependency-b-other")
    private val mDependencyCOther = Module("dependency-c-other")
    private val mDependencyD = Module("dependency-d")

    private val service = serviceWithModules()

    @Test
    fun `test empty repo has zero modules`() {
        val service = ModuleServiceImpl(ModuleRepository())

        assertThat(service.nofModules()).isEqualTo(0)
    }

    @Test
    fun `test repo with modules has non zero modules`() {
        assertThat(service.nofModules()).isEqualTo(8)
    }

    @Test
    fun `test alien module resolves to empty list`() {
        val module = Module("alien")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).isEmpty()
    }

    @Test
    fun `test globbing module-wildcard with alien module resolves to empty list`() {
        val module = Module("alien*")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).isEmpty()
    }

    @Test
    fun `test globbing wildcard-module with alien module resolves to empty list`() {
        val module = Module("*alien")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).isEmpty()
    }

    @Test
    fun `test globbing wildcard-module-wildcard with alien module resolves to empty list`() {
        val module = Module("*alien*")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).isEmpty()
    }

    @Test
    fun `test simple module resolves unchanged`() {
        val module = Module("app")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).containsExactlyInAnyOrder(module)
    }

    @Test
    fun `test gobbing wildcard finds all modules`() {
        val module = Module("*")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).containsAll(
            mApp,
            mLibA,
            mLibB,
            mLibC,
            mDependencyAOther,
            mDependencyBOther,
            mDependencyCOther
        )
    }

    @Test
    fun `test gobbing wildcard-module finds multiple modules`() {
        val module = Module("*-other")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).containsAll(mDependencyAOther, mDependencyBOther, mDependencyCOther)
    }

    @Test
    fun `test globbing wildcard-module finds module`() {
        val module = Module("*dependency-a-other")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).containsOnly(mDependencyAOther)
    }

    @Test
    fun `test globbing wildcard-module-wildcard finds module`() {
        val module = Module("*dependency-a-other*")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).containsOnly(mDependencyAOther)
    }

    @Test
    fun `test globbing module-wildcard finds multiple modules`() {
        val module = Module("lib*")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).containsAll(mLibA, mLibB, mLibC)
    }

    @Test
    fun `test globbing wildcard-module-wildcard finds multiple modules`() {
        val module = Module("*other*")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).containsAll(mDependencyAOther, mDependencyBOther, mDependencyCOther)
    }

    @Test
    fun `test globbing module-wildcard-module finds multiple modules`() {
        val module = Module("dependency*other")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).containsExactlyInAnyOrder(
            mDependencyAOther,
            mDependencyBOther,
            mDependencyCOther
        )
    }

    @Test
    fun `test globbing module-wildcard-module finds multiple modules 2`() {
        val module = Module("*end*")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).containsExactlyInAnyOrder(
            mDependencyAOther,
            mDependencyBOther,
            mDependencyCOther,
            mDependencyD
        )
    }

    @Test
    fun `test globbing normalise multiple wildcards`() {
        val module = Module("dependency****other")

        val result = service.resolvePartialModuleName(module)

        assertThat(result).containsExactlyInAnyOrder(
            mDependencyAOther,
            mDependencyBOther,
            mDependencyCOther
        )
    }


    private fun serviceWithModules(): ModuleService {
        val moduleRepository = ModuleRepository()
        moduleRepository.addModule(
            listOf(
                mApp,
                mLibA,
                mLibB,
                mLibC,
                mDependencyAOther,
                mDependencyBOther,
                mDependencyCOther,
                mDependencyD
            )
        )
        return ModuleServiceImpl(moduleRepository)
    }
}
