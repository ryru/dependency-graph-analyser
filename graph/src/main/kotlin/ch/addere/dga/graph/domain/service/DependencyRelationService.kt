package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.Module
import java.util.*

interface DependencyRelationService {

    /**
     * All modules with given configuration of the graph sorted by name.
     */
    fun allModules(configurations: Collection<Configuration>): SortedSet<Module>

    /**
     * All dependencies with given configurations of the graph sorted by origin, destination and configuration.
     */
    fun allDependencies(configurations: Collection<Configuration>): SortedSet<Dependency>

    /**
     * All sorted direct dependencies of a given module within the graph.
     */
    fun directDependenciesOf(module: Module): SortedSet<Dependency>

    /**
     * All sorted direct dependencies with a given configuration of a given module within the graph.
     */
    fun directDependenciesOf(module: Module, configurations: Configuration): SortedSet<Dependency>

    /**
     * All sorted direct dependencies with given configurations of a given module within the graph.
     */
    fun directDependenciesOf(
        module: Module,
        configurations: Collection<Configuration>
    ): SortedSet<Dependency>

    /**
     * All sorted non-direct dependencies of a given module within the graph.
     */
    fun nonDirectDependenciesOf(module: Module): SortedSet<Dependency>

    /**
     * All sorted non-direct dependencies with a given configuration of a given module within the graph.
     */
    fun nonDirectDependenciesOf(
        module: Module,
        configurations: Configuration
    ): SortedSet<Dependency>

    /**
     * All sorted non-direct dependencies with given configurations of a given module within the graph.
     */
    fun nonDirectDependenciesOf(
        module: Module,
        configurations: Collection<Configuration>
    ): SortedSet<Dependency>

    /**
     * All sorted dependencies of a given module within the graph.
     */
    fun allDependenciesOf(module: Module): SortedSet<Dependency>

    /**
     * All sorted dependencies with given a configuration of a given module within the graph.
     */
    fun allDependenciesOf(module: Module, configurations: Configuration): SortedSet<Dependency>

    /**
     * All sorted dependencies with given configurations of a given module within the graph.
     */
    fun allDependenciesOf(
        module: Module,
        configurations: Collection<Configuration>
    ): SortedSet<Dependency>
}
