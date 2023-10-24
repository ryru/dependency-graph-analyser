package ch.addere.dga.graph.application

import ch.addere.dga.graph.domain.model.Module

fun interface ModuleFilter : (Module) -> Boolean

