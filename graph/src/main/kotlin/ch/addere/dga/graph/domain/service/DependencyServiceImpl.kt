package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.Module

class DependencyServiceImpl(private val repository: DependencyRepository) : DependencyService {

    override fun nofProjectDependencies(): Int {
        return repository.getAllDependencies().size
    }

    override fun nofUniqueConfigurations(): Int {
        return repository.getAllDependencies().map { it.configuration.name }.toSet().size
    }

    override fun filteredDependencies(
        requiredModules: List<Module>,
        requiredOriginModules: List<Module>,
        requiredDestinationModules: List<Module>,
        requiredConfigurations: List<Configuration>
    ): Set<Dependency> {

        var relevantModules: Set<Dependency> = repository.getAllDependencies()
        if (requiredModules.isNotEmpty()) {
            relevantModules =
                relevantModules.filter {
                    requiredModules.contains(it.origin) || requiredModules.contains(
                        it.destination
                    )
                }
                    .toSet()
        }
        if (requiredOriginModules.isNotEmpty()) {
            relevantModules =
                relevantModules.filter { requiredOriginModules.contains(it.origin) }.toSet()
        }
        if (requiredDestinationModules.isNotEmpty()) {
            relevantModules =
                relevantModules.filter { requiredDestinationModules.contains(it.destination) }
                    .toSet()
        }
        if (requiredConfigurations.isNotEmpty()) {
            relevantModules =
                relevantModules.filter { requiredConfigurations.contains(it.configuration) }.toSet()
        }
        return relevantModules
    }
}
