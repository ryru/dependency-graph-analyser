package ch.addere.dga.core.application.service

import ch.addere.dga.core.domain.DependencyRepository
import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class DependencySearchServiceImplTest : FunSpec({

    lateinit var repository: DependencyRepository
    lateinit var service: DependencySearchService

    beforeTest {
        repository = mockk()
        service = DependencySearchServiceImpl(repository)
    }

    test("should not have dependencies on empty repository") {
        every { repository.getAllDependencies() } returns emptySet()

        val dependencies = service.findAllDependenciesUsedByModule(Module("any-module"), emptySet())

        dependencies shouldBe emptySet()
    }

    test("should not have dependencies on non-existing configuration") {
        every { repository.getAllDependencies() } returns simpleDependency

        val dependencies: Set<Dependency> = service.findAllDependenciesUsedByModule(
            Module("A"),
            setOf(Configuration("api"))
        )

        dependencies shouldBe emptySet()
    }

    test("should have dependencies on existing configuration") {
        every { repository.getAllDependencies() } returns simpleDependency

        val dependencies: Set<Dependency> = service.findAllDependenciesUsedByModule(
            Module("A"),
            setOf(Configuration("implementation"))
        )

        dependencies shouldBe setOf(
            Dependency(Module("A"), Module("B"), Configuration("implementation")),
            Dependency(Module("B"), Module("D"), Configuration("implementation")),
            Dependency(Module("D"), Module("E"), Configuration("implementation"))
        )
    }

    test("should have dependencies on existing configurations") {
        every { repository.getAllDependencies() } returns simpleDependency

        val dependencies: Set<Dependency> = service.findAllDependenciesUsedByModule(
            Module("A"),
            setOf(Configuration("implementation"), Configuration("api"))
        )

        dependencies shouldBe setOf(
            Dependency(Module("A"), Module("B"), Configuration("implementation")),
            Dependency(Module("B"), Module("C"), Configuration("api")),
            Dependency(Module("B"), Module("D"), Configuration("implementation")),
            Dependency(Module("D"), Module("E"), Configuration("implementation"))
        )
    }

    test("should have one dependencies on second last node") {
        every { repository.getAllDependencies() } returns simpleDependency

        val dependencies: Set<Dependency> = service.findAllDependenciesUsedByModule(
            Module("D"),
            setOf(Configuration("implementation"))
        )

        dependencies shouldBe setOf(
            Dependency(
                Module("D"),
                Module("E"),
                Configuration("implementation")
            )
        )
    }

    test("should not have dependencies on leaf") {
        every { repository.getAllDependencies() } returns simpleDependency

        val dependencies: Set<Dependency> = service.findAllDependenciesUsedByModule(
            Module("E"),
            setOf(Configuration("implementation"))
        )

        dependencies shouldBe emptySet()
    }
})

private val simpleDependency: Set<Dependency> = setOf(
    Dependency(Module("A"), Module("B"), Configuration("implementation")),
    Dependency(Module("B"), Module("C"), Configuration("api")),
    Dependency(Module("B"), Module("D"), Configuration("implementation")),
    Dependency(Module("D"), Module("E"), Configuration("implementation"))
)
