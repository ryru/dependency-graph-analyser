package ch.addere.dga.graph.domain.model

fun interface ModuleFilter : (Module) -> Boolean

val matchAnyModule = ModuleFilter { _ -> true }
