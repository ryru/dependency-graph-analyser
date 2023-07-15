package ch.addere.mdg.graph.domain.model.graph

import ch.addere.mdg.graph.domain.model.Configuration

data class DependencyEdge(
    val origin: ModuleVertex,
    val destination: ModuleVertex,
    val configuration: Configuration
)
