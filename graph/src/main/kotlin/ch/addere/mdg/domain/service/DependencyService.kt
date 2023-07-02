package ch.addere.mdg.domain.service

import ch.addere.mdg.domain.model.Configuration
import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module
import java.util.*

interface DependencyService {

    /**
     * All modules of the graph sorted by name.
     */
    fun allModules(): SortedSet<Module>

    /**
     * All modules with given configuration of the graph sorted by name.
     */
    fun allModules(vararg configurations: Configuration): SortedSet<Module>

    /**
     * All dependencies of the graph sorted by origin, destination and configuration.
     */
    fun allDependencies(): SortedSet<Dependency>

    /**
     * All sorted direct dependencies of a given module within the graph.
     */
    fun directDependenciesOf(module: Module): SortedSet<Dependency>

    /**
     * All sorted non-direct dependencies of a given module within the graph.
     */
    fun nonDirectDependenciesOf(module: Module): SortedSet<Dependency>

    /**
     * All sorted dependencies of a given module within the graph.
     */
    fun allDependenciesOf(module: Module): SortedSet<Dependency>
}
