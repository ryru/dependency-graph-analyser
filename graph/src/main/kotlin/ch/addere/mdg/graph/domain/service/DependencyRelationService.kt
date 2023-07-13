package ch.addere.mdg.graph.domain.service

import ch.addere.mdg.graph.domain.model.Configuration
import ch.addere.mdg.graph.domain.model.Dependency
import ch.addere.mdg.graph.domain.model.Module
import java.util.*

interface DependencyRelationService {

    /**
     * All modules with given configuration of the graph sorted by name.
     */
    fun allModules(vararg configurations: Configuration): SortedSet<Module>

    /**
     * All dependencies with given configurations of the graph sorted by origin, destination and configuration.
     */
    fun allDependencies(configurations: Collection<Configuration>): SortedSet<Dependency>

    /**
     * All sorted direct dependencies of a given module within the graph.
     */
    fun directDependenciesOf(module: Module): SortedSet<Dependency>

    /**
     * All sorted direct dependencies with given configurations of a given module within the graph.
     */
    fun directDependenciesOf(
        module: Module,
        vararg configurations: Configuration
    ): SortedSet<Dependency>

    /**
     * All sorted non-direct dependencies of a given module within the graph.
     */
    fun nonDirectDependenciesOf(module: Module): SortedSet<Dependency>

    /**
     * All sorted non-direct dependencies with given configurations of a given module within the graph.
     */
    fun nonDirectDependenciesOf(
        module: Module,
        vararg configurations: Configuration
    ): SortedSet<Dependency>

    /**
     * All sorted dependencies of a given module within the graph.
     */
    fun allDependenciesOf(module: Module): SortedSet<Dependency>

    /**
     * All sorted dependencies with given configurations of a given module within the graph.
     */
    fun allDependenciesOf(
        module: Module,
        vararg configurations: Configuration
    ): SortedSet<Dependency>
}
