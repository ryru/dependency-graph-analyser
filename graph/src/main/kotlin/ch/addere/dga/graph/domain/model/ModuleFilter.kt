package ch.addere.dga.graph.domain.model

fun interface ModuleFilter : (Module) -> Boolean

val matchAny = ModuleFilter { _ -> true }
