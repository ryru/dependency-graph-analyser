package ch.addere.mdg.domain.model.graph

import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module
import java.util.*

class ModuleDependencyDag {

    private val modules: MutableSet<ModuleVertex> = mutableSetOf()
    private val dependencies: MutableSet<DependencyEdge> = mutableSetOf()

    fun addModule(module: Module) {
        resolve(module)
    }

    fun addAllModule(vararg modules: Module) {
        modules.forEach { addModule(it) }
    }

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
        val vertex = Optional.ofNullable(modules.firstOrNull { it.module == module })
        if (vertex.isEmpty) {
            val newVertex = ModuleVertex(module)
            modules.add(newVertex)
            return newVertex
        }
        return vertex.get()
    }

    fun nofModules(): Int {
        return modules.size
    }

    fun nofDependencies(): Int {
        return dependencies.size
    }

    fun modules(): Set<ModuleVertex> {
        return modules
    }

    fun dependencies(): Set<DependencyEdge> {
        return dependencies
    }
}
