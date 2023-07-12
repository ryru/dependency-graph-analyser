package ch.addere.mdg.graph.domain.service

import ch.addere.mdg.graph.domain.model.Configuration
import ch.addere.mdg.graph.domain.model.Dependency
import ch.addere.mdg.graph.domain.model.Module
import ch.addere.mdg.graph.domain.model.graph.DependencyEdge
import ch.addere.mdg.graph.domain.model.graph.ModuleDependencyDag
import ch.addere.mdg.graph.domain.model.graph.ModuleVertex
import java.util.*

class DependencyServiceImpl(private val dag: ModuleDependencyDag) : DependencyService {

    override fun allModules(vararg configurations: Configuration): SortedSet<Module> {
        return findAllModulesWithGivenConfiguration(dag.vertices(), configurations.toSet())
    }

    private fun findAllModulesWithGivenConfiguration(
        vertices: Set<ModuleVertex>,
        configurations: Set<Configuration>
    ): SortedSet<Module> {
        return vertices
            .associate { toModule(it) to toConfiguration(it) }
            .filter { hasModuleAtLeastOneConfiguration(it, configurations) }
            .map { it.key }
            .toSortedSet()
    }

    private fun toModule(moduleVertex: ModuleVertex) = moduleVertex.module

    private fun toConfiguration(moduleVertex: ModuleVertex): List<Configuration> =
        moduleVertex.getOutgoing().values
            .flatten()
            .map { it.configuration }
            .toList()

    private fun hasModuleAtLeastOneConfiguration(
        a: Map.Entry<Module, List<Configuration>>,
        configurations: Set<Configuration>
    ) = a.value.intersect(configurations.toSet()).isNotEmpty()

    override fun allDependencies(): SortedSet<Dependency> {
        return dag.dependencies().map(::toDependency).sorted().toSortedSet()
    }

    override fun allDependencies(vararg configurations: Configuration): SortedSet<Dependency> {
        return allDependencies().filter { hasConfiguration(configurations, it) }.toSortedSet()
    }

    override fun directDependenciesOf(module: Module): SortedSet<Dependency> {
        return dag.vertices().filter { it.module == module }.flatMap { toDependencies(it) }
            .toSortedSet()
    }

    override fun directDependenciesOf(
        module: Module,
        vararg configurations: Configuration
    ): SortedSet<Dependency> {
        return directDependenciesOf(module).filter { hasConfiguration(configurations, it) }
            .toSortedSet()
    }

    override fun nonDirectDependenciesOf(module: Module): SortedSet<Dependency> {
        return directDependenciesOf(module).flatMap { allDependenciesOf(it.destination) }
            .toSortedSet()
    }

    override fun nonDirectDependenciesOf(
        module: Module,
        vararg configurations: Configuration
    ): SortedSet<Dependency> {
        return nonDirectDependenciesOf(module).filter { hasConfiguration(configurations, it) }
            .toSortedSet()
    }

    override fun allDependenciesOf(module: Module): SortedSet<Dependency> {
        return nonDirectDependenciesOf(module).plus(directDependenciesOf(module)).toSortedSet()
    }

    override fun allDependenciesOf(
        module: Module,
        vararg configurations: Configuration
    ): SortedSet<Dependency> {
        return allDependenciesOf(module).filter { hasConfiguration(configurations, it) }
            .toSortedSet()
    }

    private fun hasConfiguration(configurations: Array<out Configuration>, it: Dependency) =
        configurations.contains(it.configuration)

    private fun toDependencies(vertex: ModuleVertex): List<Dependency> =
        vertex.getOutgoing().flatMap { it.value }.map(::toDependency)

    private fun toDependency(edge: DependencyEdge): Dependency =
        Dependency(edge.origin.module, edge.destination.module, edge.configuration)
}
