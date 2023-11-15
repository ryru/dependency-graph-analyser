package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.FilteredConfiguration
import ch.addere.dga.graph.domain.model.FilteredModules

class DependencyServiceImpl(private val repository: DependencyRepository) : DependencyService {

    override fun nofProjectDependencies(): Int {
        return repository.getAllDependencies().size
    }

    override fun nofUniqueConfigurations(): Int {
        return repository.getAllDependencies().map { it.configuration.name }.toSet().size
    }

    override fun filteredDependencies(
        filteredModules: FilteredModules,
        filteredOrigin: FilteredModules,
        filteredDestination: FilteredModules,
        filteredConfiguration: FilteredConfiguration
    ): Set<Dependency> {

        var relevantModules: Set<Dependency> = repository.getAllDependencies()
        if (filteredModules.isApplicable) {
            relevantModules =
                relevantModules.filter { filteredModules.contains(it.origin, it.destination) }
                    .toSet()
        }
        if (filteredOrigin.isApplicable) {
            relevantModules = relevantModules.filter { filteredOrigin.contains(it.origin) }.toSet()
        }
        if (filteredDestination.isApplicable) {
            relevantModules =
                relevantModules.filter { filteredDestination.contains(it.destination) }.toSet()
        }
        if (filteredConfiguration.isApplicable) {
            relevantModules =
                relevantModules.filter { filteredConfiguration.contains(it.configuration) }.toSet()
        }
        return relevantModules
    }
}
