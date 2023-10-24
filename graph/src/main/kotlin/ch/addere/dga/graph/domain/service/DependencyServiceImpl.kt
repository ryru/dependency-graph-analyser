package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.application.DependencyService
import ch.addere.dga.graph.application.ModuleFilter
import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency

class DependencyServiceImpl(private val dependencyRepository: DependencyRepository) :
    DependencyService {

    override fun nofDependencies(): Int {
        return dependencyRepository.getAllDependencies().size
    }

    override fun nofUniqueDependencies(): Int {
        return dependencyRepository.getAllDependencies().map { it.configuration.name }.toSet().size
    }

    override fun configurationsWithOccurrence(): Map<Configuration, Int> {
        return dependencyRepository.getAllDependencies()
            .associate { it.configuration to countNofConfigurations(it.configuration) }
    }

    private fun countNofConfigurations(configuration: Configuration): Int {
        return dependencyRepository.getDependencyByConfiguration(configuration).count()
    }

    override fun dependencies(filter: ModuleFilter): Set<Dependency> {
        return dependenciesMatchOrigin(filter) + dependenciesMatchDestination(filter)
    }

    private fun dependenciesMatchOrigin(filter: ModuleFilter): Set<Dependency> {
        return dependencyRepository.getAllDependencies()
            .filter { dependency -> filter.invoke(dependency.origin) }
            .toSet()
    }

    private fun dependenciesMatchDestination(filter: ModuleFilter): Set<Dependency> {
        return dependencyRepository.getAllDependencies()
            .filter { dependency -> filter.invoke(dependency.destination) }
            .toSet()
    }
}
