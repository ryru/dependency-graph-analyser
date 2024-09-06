package ch.addere.dga.core.domain.model

fun interface ConfigurationFilter : (Configuration) -> Boolean

val matchAnyConfiguration = ConfigurationFilter { _ -> true }
