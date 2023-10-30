package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.Module

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
        requiredModules: List<Module>,
        requiredOriginModules: List<Module>,
        requiredDestinationModules: List<Module>,
        requiredConfigurations: List<Configuration>
    ): Set<Dependency>
}
