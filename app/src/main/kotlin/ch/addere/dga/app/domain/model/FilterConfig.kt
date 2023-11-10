package ch.addere.dga.app.domain.model

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Module

data class FilterConfig(
    val modules: List<Module>,
    val originModules: List<Module>,
    val destinationModules: List<Module>,
    val configurations: List<Configuration>
)
