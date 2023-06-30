package ch.addere.mdg.domain.model

data class Dependency(val origin: Module, val destination: Module, val configuration: Configuration)
