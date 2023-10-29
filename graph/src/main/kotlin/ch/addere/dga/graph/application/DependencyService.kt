package ch.addere.dga.graph.application

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
     * Returns all dependencies.
     */
    fun allDependencies(): Set<Dependency>

    /**
     * Returns all dependencies.
     */
    fun filterDependencies(modules: Collection<Module>): Set<Dependency>

    /**
     * Returns all dependencies.
     */
    fun filterDependenciesByOrigin(modules: Collection<Module>): Set<Dependency>

    /**
     * Returns all dependencies.
     */
    fun filterDependenciesByDestination(modules: Collection<Module>): Set<Dependency>
}
