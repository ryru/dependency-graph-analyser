package ch.addere.mdg.domain.model.graph

import ch.addere.mdg.domain.model.Configuration

data class DependencyEdge(
    val origin: ModuleVertex,
    val destination: ModuleVertex,
    val configuration: Configuration
) {
    fun getEndpoints(): Pair<ModuleVertex, ModuleVertex> {
        return Pair(origin, destination)
    }
}
