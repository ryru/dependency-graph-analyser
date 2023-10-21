package ch.addere.dga.graph.domain.model.graph

import ch.addere.dga.graph.domain.model.Configuration

data class DependencyEdge(
    val origin: ModuleVertex,
    val destination: ModuleVertex,
    val configuration: Configuration
)
