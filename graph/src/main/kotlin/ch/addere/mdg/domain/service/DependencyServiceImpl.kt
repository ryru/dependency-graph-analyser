package ch.addere.mdg.domain.service

import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module
import ch.addere.mdg.domain.model.graph.DependencyEdge
import ch.addere.mdg.domain.model.graph.ModuleDependencyDag
import ch.addere.mdg.domain.model.graph.ModuleVertex
import java.util.*

class DependencyServiceImpl(private val dag: ModuleDependencyDag) : DependencyService {

    override fun allModules(): SortedSet<Module> {
        return dag.modules().map(::toModule).toSortedSet()
    }

    private fun toModule(moduleVertex: ModuleVertex) = moduleVertex.module

    override fun allDependencies(): SortedSet<Dependency> {
        return dag.dependencies().map(::toDependency).sorted().toSortedSet()
    }

    override fun directDependencies(module: Module): SortedSet<Dependency> {
        return dag.modules().filter { it.module == module }.flatMap { toDependencies(it) }
            .toSortedSet()
    }

    override fun nonDirectDependencies(module: Module): SortedSet<Dependency> {
        return directDependencies(module).flatMap { dependencies(it.destination) }.toSortedSet()
    }

    override fun dependencies(module: Module): SortedSet<Dependency> {
        return nonDirectDependencies(module).plus(directDependencies(module)).toSortedSet()
    }

    private fun toDependencies(vertex: ModuleVertex): List<Dependency> =
        vertex.getOutgoing().flatMap { it.value }.map(::toDependency)

    private fun toDependency(edge: DependencyEdge): Dependency =
        Dependency(edge.origin.module, edge.destination.module, edge.configuration)
}
