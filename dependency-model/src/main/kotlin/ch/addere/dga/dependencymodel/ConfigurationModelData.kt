package ch.addere.dga.dependencymodel

import java.io.Serializable

data class ConfigurationModelData(
    override val configuration: String,
    override val dependencyString: Dependency,
    override val destination: ProjectModule
) : ConfigurationModel, Serializable
