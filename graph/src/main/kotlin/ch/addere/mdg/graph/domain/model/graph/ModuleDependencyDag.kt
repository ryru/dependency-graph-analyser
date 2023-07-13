package ch.addere.mdg.graph.domain.model.graph

import ch.addere.mdg.graph.domain.model.Configuration
import ch.addere.mdg.graph.domain.model.Dependency
import ch.addere.mdg.graph.domain.model.Module
import ch.addere.mdg.graph.domain.service.DependencyRepository
import ch.addere.mdg.graph.domain.service.ModuleRepository
import java.util.*

class ModuleDependencyDag(
    moduleRepository: ModuleRepository,
    dependencyRepository: DependencyRepository
) {

    private val vertices = moduleRepository.getAllModules().map { ModuleVertex(it) }.toSet()
    private val edges = dependencyRepository.getAllDependencies().map(::toDependencyEdge).toSet()

    private fun toDependencyEdge(dependency: Dependency): DependencyEdge {
        val origin = resolve(dependency.origin)
        val destination = resolve(dependency.destination)
        val dependencyEdge = DependencyEdge(origin, destination, dependency.configuration)

        origin.addOutgoing(destination, dependencyEdge)
        destination.addIncoming(origin, dependencyEdge)

        return dependencyEdge
    }

    private fun resolve(module: Module): ModuleVertex {
        return vertices.first { it.module == module }
    }

    fun nofVertices(): Int {
        return vertices.size
    }

    fun nofEdges(): Int {
        return edges.size
    }

    fun vertices(): Set<ModuleVertex> {
        return vertices
    }

    fun vertex(module: Module): Optional<ModuleVertex> {
        return Optional.ofNullable(vertices.firstOrNull { it.module == module })
    }

    fun edges(): Set<DependencyEdge> {
        return edges
    }

    fun edge(configuration: Collection<Configuration>): Set<DependencyEdge> {
        return edges.filter { configuration.contains(it.configuration) }.toSet()
    }
}
