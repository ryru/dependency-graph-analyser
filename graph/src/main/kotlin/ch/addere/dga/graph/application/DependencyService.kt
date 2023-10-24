package ch.addere.dga.graph.application

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency

interface DependencyService {

    /**
     * Returns the number of dependencies.
     */
    fun nofDependencies(): Int

    /**
     * Returns the number of unique dependencies.
     */
    fun nofUniqueDependencies(): Int

    /**
     * Returns all dependencies with number of occurrence.
     */
    fun configurationsWithOccurrence(): Map<Configuration, Int>

    /**
     * Returns all dependencies matching the filter.
     */
    fun dependencies(filter: ModuleFilter): Set<Dependency>
}
