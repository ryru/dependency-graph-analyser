package ch.addere.dga.core.domain.model.graph

import ch.addere.dga.core.domain.model.Configuration

data class DependencyEdge(
    val origin: ModuleVertex,
    val destination: ModuleVertex,
    val configuration: Configuration
)
