package ch.addere.mdg.importergradlemodel

import java.io.Serializable

data class DependencyImpl(
    override val name: String,
    override val group: String? = "",
    override val version: String? = ""
) : Dependency, Serializable
