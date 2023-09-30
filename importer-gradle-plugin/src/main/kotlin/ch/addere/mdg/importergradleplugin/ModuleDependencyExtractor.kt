package ch.addere.mdg.importergradleplugin

import ch.addere.mdg.importergradlemodel.ConfigurationModel
import ch.addere.mdg.importergradlemodel.ConfigurationModelData
import ch.addere.mdg.importergradlemodel.Dependency
import ch.addere.mdg.importergradlemodel.DependencyImpl
import ch.addere.mdg.importergradlemodel.ProjectModule
import ch.addere.mdg.importergradlemodel.ProjectModuleImpl
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
