package ch.addere.mdg.domain.service

import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module
import ch.addere.mdg.domain.model.graph.DependencyEdge
import ch.addere.mdg.domain.model.graph.ModuleDependencyDag
import ch.addere.mdg.domain.model.graph.ModuleVertex

class DependencyService(private val dag: ModuleDependencyDag) {

    /**
     * All modules of the graph sorted by name.
     */
    fun allModules(): Set<Module> {
        return dag.modules().map(::toModule).toSortedSet()
    }

    private fun toModule(moduleVertex: ModuleVertex) = moduleVertex.module

    /**
     * All dependencies of the graph sorted by origin, destination and configuration.
     */
    fun allDependencies(): Set<Dependency> {
        return dag.dependencies().map(::toDependency).sorted().toSet()
    }

    /**
     * All direct dependencies of a given module within the graph.
     */
    fun directDependencies(module: Module): Set<Dependency> {
        return dag.modules().filter { it.module == module }.flatMap { toDependencies(it) }.toSet()
    }

    /**
     * All non-direct dependencies of a given module within the graph.
     */
    fun nonDirectDependencies(module: Module): Set<Dependency> {
        return directDependencies(module).flatMap { dependencies(it.destination) }.toSet()
    }

    /**
     * All dependencies of a given module within the graph.
     */
    fun dependencies(module: Module): Set<Dependency> {
        return nonDirectDependencies(module).plus(directDependencies(module))
    }

    private fun toDependencies(vertex: ModuleVertex): List<Dependency> =
        vertex.getOutgoing().flatMap { it.value }.map(::toDependency)

    private fun toDependency(edge: DependencyEdge): Dependency =
        Dependency(edge.origin.module, edge.destination.module, edge.configuration)
}
