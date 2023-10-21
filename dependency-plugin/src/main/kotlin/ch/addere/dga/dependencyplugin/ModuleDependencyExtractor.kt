package ch.addere.dga.dependencyplugin

import ch.addere.dga.dependencymodel.ConfigurationModel
import ch.addere.dga.dependencymodel.ConfigurationModelData
import ch.addere.dga.dependencymodel.Dependency
import ch.addere.dga.dependencymodel.DependencyImpl
import ch.addere.dga.dependencymodel.ProjectModule
import ch.addere.dga.dependencymodel.ProjectModuleImpl
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer

fun extractModuleDependencies(
    project: Project,
    modules: Set<ProjectModule>
): Map<String, List<ConfigurationModel>> {
    return project.allprojects
        .associate { it.name to extractConfiguration(it.configurations, modules) }
}

private fun extractConfiguration(
    configurationContainer: ConfigurationContainer,
    modules: Set<ProjectModule>
): List<ConfigurationModel> {
    return configurationContainer
        .flatMap { configurationToDependencies(it, modules) }
        .toList()
}

private fun configurationToDependencies(
    configuration: Configuration,
    modules: Set<ProjectModule>
): List<ConfigurationModel> {
    val configName = configuration.name
    return configuration.dependencies
        .map { DependencyImpl(it.name, it.group, it.version) }
        .filter { isDependencyWithinProjectModules(modules, it) }
        .map { ConfigurationModelData(configName, it, ProjectModuleImpl(it.name)) }
        .toList()
}

private fun isDependencyWithinProjectModules(
    modules: Set<ProjectModule>,
    dependency: Dependency
): Boolean {
    return modules.map { it.projectModule }.contains(dependency.name)
}
