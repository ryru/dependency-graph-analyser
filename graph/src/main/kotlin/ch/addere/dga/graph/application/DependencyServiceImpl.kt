package ch.addere.dga.graph.application

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.Module
import ch.addere.dga.graph.domain.model.ModuleFilter
import ch.addere.dga.graph.domain.model.matchAny
import ch.addere.dga.graph.domain.service.DependencyRepository

class DependencyServiceImpl(private val repository: DependencyRepository) : DependencyService {

    override fun nofProjectDependencies(): Int {
        return repository.getAllDependencies().size
    }

    override fun nofUniqueConfigurations(): Int {
        return repository.getAllDependencies().map { it.configuration.name }.toSet().size
    }

    override fun allDependencies(): Set<Dependency> {
        return filterDependencies(matchAny)
    }

    override fun filterDependencies(modules: Collection<Module>): Set<Dependency> {
        return filterDependencies { module -> modules.contains(module) }
    }

    override fun filterDependenciesByOrigin(modules: Collection<Module>): Set<Dependency> {
        return dependenciesMatchOrigin { module -> modules.contains(module) }
    }

    override fun filterDependenciesByDestination(modules: Collection<Module>): Set<Dependency> {
        return dependenciesMatchDestination { module -> modules.contains(module) }
    }

    private fun filterDependencies(moduleFilter: ModuleFilter): Set<Dependency> {
        return dependenciesMatchOrigin(moduleFilter) + dependenciesMatchDestination(moduleFilter)
    }

    private fun countNofConfigurations(configuration: Configuration): Int {
        return repository.getDependencyByConfiguration(configuration).count()
    }

    private fun dependenciesMatchOrigin(filter: ModuleFilter): Set<Dependency> {
        return repository.getAllDependencies()
            .filter { dependency -> filter.invoke(dependency.origin) }
            .toSet()
    }

    private fun dependenciesMatchDestination(filter: ModuleFilter): Set<Dependency> {
        return repository.getAllDependencies()
            .filter { dependency -> filter.invoke(dependency.destination) }
            .toSet()
    }
}
