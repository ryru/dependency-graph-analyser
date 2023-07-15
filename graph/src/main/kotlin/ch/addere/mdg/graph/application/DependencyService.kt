package ch.addere.mdg.graph.application

import ch.addere.mdg.graph.domain.model.Configuration

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
    fun configuraitonsWithOccurence(): Map<Configuration, Int>
}
