package ch.addere.dga.core.domain.service

import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module
import ch.addere.dga.core.domain.model.graph.DependencyEdge
import ch.addere.dga.core.domain.model.graph.ModuleDependencyDag
import ch.addere.dga.core.domain.model.graph.ModuleVertex

import java.util.*

class DependencyRelationServiceImpl(private val dag: ModuleDependencyDag) :
    DependencyRelationService {

    override fun allModules(configurations: Collection<Configuration>): SortedSet<Module> {
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

    override fun allDependencies(configurations: Collection<Configuration>): SortedSet<Dependency> {
        return dag.edge(configurations).map(::toDependency).toSortedSet()
    }

    override fun directDependenciesOf(module: Module): SortedSet<Dependency> {
        return dag.vertex(module).map { toDependencies(it) }.orElse(emptyList()).toSortedSet()
    }

    override fun directDependenciesOf(
        module: Module,
        configurations: Configuration
    ): SortedSet<Dependency> {
        return findAllDependenciesWithConfiguration(directDependenciesOf(module), configurations)
    }

    override fun directDependenciesOf(
        module: Module,
        configurations: Collection<Configuration>
    ): SortedSet<Dependency> {
        return findAllDependenciesWithConfigurations(directDependenciesOf(module), configurations)
    }

    override fun nonDirectDependenciesOf(module: Module): SortedSet<Dependency> {
        return directDependenciesOf(module).flatMap { allDependenciesOf(it.destination) }
            .toSortedSet()
    }

    override fun nonDirectDependenciesOf(
        module: Module,
        configurations: Configuration
    ): SortedSet<Dependency> {
        return findAllDependenciesWithConfiguration(nonDirectDependenciesOf(module), configurations)
    }

    override fun nonDirectDependenciesOf(
        module: Module,
        configurations: Collection<Configuration>
    ): SortedSet<Dependency> {
        return findAllDependenciesWithConfigurations(
            nonDirectDependenciesOf(module),
            configurations
        )
    }

    override fun allDependenciesOf(module: Module): SortedSet<Dependency> {
        return nonDirectDependenciesOf(module).plus(directDependenciesOf(module)).toSortedSet()
    }

    override fun allDependenciesOf(
        module: Module,
        configurations: Configuration
    ): SortedSet<Dependency> {
        return findAllDependenciesWithConfiguration(allDependenciesOf(module), configurations)
    }

    override fun allDependenciesOf(
        module: Module,
        configurations: Collection<Configuration>
    ): SortedSet<Dependency> {
        return findAllDependenciesWithConfigurations(allDependenciesOf(module), configurations)
    }

    private fun toDependencies(vertex: ModuleVertex): List<Dependency> =
        vertex.getOutgoing().flatMap { it.value }.map(::toDependency)

    private fun toDependency(edge: DependencyEdge): Dependency =
        Dependency(edge.origin.module, edge.destination.module, edge.configuration)

    private fun findAllDependenciesWithConfiguration(
        dependencies: Collection<Dependency>,
        configuration: Configuration
    ): SortedSet<Dependency> {
        return dependencies.filter { configuration == it.configuration }.toSortedSet()
    }

    private fun findAllDependenciesWithConfigurations(
        dependencies: Collection<Dependency>,
        configurations: Collection<Configuration>
    ): SortedSet<Dependency> {
        return dependencies.filter { configurations.contains(it.configuration) }.toSortedSet()
    }
}
