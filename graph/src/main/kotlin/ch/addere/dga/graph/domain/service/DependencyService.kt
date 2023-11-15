package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.FilteredConfiguration
import ch.addere.dga.graph.domain.model.FilteredModules

interface DependencyService {

    /**
     * Returns the total number of sub-/project dependencies.
     */
    fun nofProjectDependencies(): Int

    /**
     * Returns the total number of unique configurations.
     */
    fun nofUniqueConfigurations(): Int

    /**
     * Returns only dependencies matching all requirements.
     */
    fun filteredDependencies(
        filteredModules: FilteredModules,
        filteredOrigin: FilteredModules,
        filteredDestination: FilteredModules,
        filteredConfiguration: FilteredConfiguration
    ): Set<Dependency>
}
