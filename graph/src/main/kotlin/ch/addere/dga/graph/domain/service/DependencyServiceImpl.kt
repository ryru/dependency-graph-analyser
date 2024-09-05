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

        var filteredDependencies: Set<Dependency> = repository.getAllDependencies()
        if (filteredModules.isApplicable) {
            filteredDependencies =
                filteredDependencies.filter { filteredModules.contains(it.origin, it.destination) }
                    .toSet()
        }
        if (filteredOrigin.isApplicable) {
            filteredDependencies =
                filteredDependencies.filter { filteredOrigin.contains(it.origin) }.toSet()
        }
        if (filteredDestination.isApplicable) {
            filteredDependencies =
                filteredDependencies.filter { filteredDestination.contains(it.destination) }.toSet()
        }
        if (filteredConfiguration.isApplicable) {
            filteredDependencies =
                filteredDependencies.filter { filteredConfiguration.contains(it.configuration) }
                    .toSet()
        }
        return filteredDependencies
    }
}
