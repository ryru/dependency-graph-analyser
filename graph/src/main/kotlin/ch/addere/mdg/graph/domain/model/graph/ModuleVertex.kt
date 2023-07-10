package ch.addere.mdg.graph.domain.model.graph

import ch.addere.mdg.graph.domain.model.Module

class ModuleVertex(val module: Module) {

    private val outgoing: MutableMap<ModuleVertex, MutableSet<DependencyEdge>> = mutableMapOf()
    private val incoming: MutableMap<ModuleVertex, MutableSet<DependencyEdge>> = mutableMapOf()

    fun addOutgoing(destination: ModuleVertex, dependencyEdge: DependencyEdge) {
        insertDependency(destination, dependencyEdge, outgoing)
    }

    fun addIncoming(origin: ModuleVertex, dependencyEdge: DependencyEdge) {
        insertDependency(origin, dependencyEdge, incoming)
    }

    private fun insertDependency(
        vertex: ModuleVertex,
        dependencyEdge: DependencyEdge,
        map: MutableMap<ModuleVertex, MutableSet<DependencyEdge>>
    ) {
        if (!map.contains(vertex)) {
            map[vertex] = mutableSetOf(dependencyEdge)
        } else if (map.getValue(vertex).contains(dependencyEdge)) {
            throw IllegalArgumentException("""Dependency of module '${vertex.module.name}' and configuration '${dependencyEdge.configuration.name}' already exists""")
        } else {
            map[vertex]!!.add(dependencyEdge)
        }
    }

    fun getOutgoing(): Map<ModuleVertex, Set<DependencyEdge>> {
        return outgoing
    }

    fun getIncoming(): Map<ModuleVertex, Set<DependencyEdge>> {
        return incoming
    }
}
