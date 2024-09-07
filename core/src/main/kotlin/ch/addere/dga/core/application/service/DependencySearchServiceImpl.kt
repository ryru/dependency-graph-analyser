package ch.addere.dga.core.application.service

import ch.addere.dga.core.domain.DependencyRepository
import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module

class DependencySearchServiceImpl(private val dependencyRepository: DependencyRepository) :
    DependencySearchService {

    override fun findAllDependenciesDependingOnModule(
        module: Module,
        withConfigurations: Collection<Configuration>
    ): Set<Dependency> {
        return dependenciesWithDestination(module, withConfigurations)
    }

    override fun findAllDependenciesUsedByModule(
        module: Module,
        withConfigurations: Collection<Configuration>
    ): Set<Dependency> {
        return dependenciesWithOrigin(module, withConfigurations)
    }

    private fun dependenciesWithDestination(
        destination: Module,
        withConfigurations: Collection<Configuration>
    ): Set<Dependency> {
        val allDependenciesWithSameDestination: Set<Dependency> =
            dependencyRepository.getAllDependencies()
                .filter { dependency ->
                    dependency.destination == destination && withConfigurations.contains(
                        dependency.configuration
                    )
                }
                .toSet()

        return if (allDependenciesWithSameDestination.isEmpty()) {
            emptySet()
        } else {
            allDependenciesWithSameDestination
                .flatMap { dependenciesWithDestination(it.origin, withConfigurations) }
                .toSet() union allDependenciesWithSameDestination
        }
    }

    private fun dependenciesWithOrigin(
        origin: Module,
        withConfigurations: Collection<Configuration>
    ): Set<Dependency> {
        println("call with origin = $origin")
        val allDependenciesWithSameOrigin: Set<Dependency> =
            dependencyRepository.getAllDependencies()
                .filter { dependency ->
                    dependency.origin == origin && withConfigurations.contains(
                        dependency.configuration
                    )
                }
                .toSet()
        println(allDependenciesWithSameOrigin.joinToString(", "))

        return if (allDependenciesWithSameOrigin.isEmpty()) {
            emptySet()
        } else {
            allDependenciesWithSameOrigin
                .flatMap { dependenciesWithOrigin(it.destination, withConfigurations) }
                .toSet() union allDependenciesWithSameOrigin
        }
    }
}
