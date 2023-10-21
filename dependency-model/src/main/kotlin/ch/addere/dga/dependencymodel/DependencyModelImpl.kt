package ch.addere.dga.dependencymodel

import java.io.Serializable

data class DependencyModelImpl(
    override val projectName: String,
    override val projectModules: Set<ProjectModule>,
    override val projectModuleDependencies: Map<String, List<ConfigurationModel>>
) : DependencyModel,
    Serializable
