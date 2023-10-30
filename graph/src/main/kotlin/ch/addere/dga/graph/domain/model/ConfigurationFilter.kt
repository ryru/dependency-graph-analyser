package ch.addere.dga.graph.domain.model

fun interface ConfigurationFilter : (Configuration) -> Boolean

val matchAnyConfiguration = ConfigurationFilter { _ -> true }
