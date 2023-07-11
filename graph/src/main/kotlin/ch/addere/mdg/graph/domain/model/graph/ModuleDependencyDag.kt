package ch.addere.mdg.graph.domain.model.graph

import ch.addere.mdg.graph.domain.model.Dependency
import ch.addere.mdg.graph.domain.model.Module
import ch.addere.mdg.graph.domain.service.ModuleRepository

class ModuleDependencyDag(moduleRepository: ModuleRepository) {

    private val vertices = moduleRepository.getAllModules().map { ModuleVertex(it) }.toSet()
    private val dependencies: MutableSet<DependencyEdge> = mutableSetOf()

    fun addDependency(dependency: Dependency) {
        val origin = resolve(dependency.origin)
        val destination = resolve(dependency.destination)
        val dependencyEdge = DependencyEdge(origin, destination, dependency.configuration)

        if (dependencies.contains(dependencyEdge)) {
            throw IllegalArgumentException(
                """Dependency of type '${dependencyEdge.configuration.name}' between origin '${dependencyEdge.origin.module.name}' and destination '${dependencyEdge.destination.module.name}' already exists"""
            )
        }
        dependencies.add(dependencyEdge)

        origin.addOutgoing(destination, dependencyEdge)
        destination.addIncoming(origin, dependencyEdge)
    }

    fun addAllDependency(vararg dependencies: Dependency) {
        dependencies.forEach { addDependency(it) }
    }

    private fun resolve(module: Module): ModuleVertex {
        return vertices.first { it.module == module }
    }

    fun nofVertices(): Int {
        return vertices.size
    }

    fun nofDependencies(): Int {
        return dependencies.size
    }

    fun vertices(): Set<ModuleVertex> {
        return vertices
    }

    fun dependencies(): Set<DependencyEdge> {
        return dependencies
    }
}
