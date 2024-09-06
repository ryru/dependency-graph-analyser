package ch.addere.dga.core.domain.model

fun interface ModuleFilter : (Module) -> Boolean

val matchAnyModule = ModuleFilter { _ -> true }
