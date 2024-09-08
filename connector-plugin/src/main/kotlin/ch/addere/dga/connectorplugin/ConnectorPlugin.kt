package ch.addere.dga.connectorplugin

import ch.addere.dga.connectormodel.ConfigurationModel
import ch.addere.dga.connectormodel.ConfigurationModelData
import ch.addere.dga.connectormodel.Dependency
import ch.addere.dga.connectormodel.DependencyImpl
import ch.addere.dga.connectormodel.DependencyModel
import ch.addere.dga.connectormodel.DependencyModelImpl
import ch.addere.dga.connectormodel.ProjectModule
import ch.addere.dga.connectormodel.ProjectModuleImpl
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationContainer
import org.gradle.tooling.provider.model.ToolingModelBuilder
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry
import javax.inject.Inject

class ConnectorPlugin @Inject constructor(private val registry: ToolingModelBuilderRegistry) :
    Plugin<Project> {

    override fun apply(target: Project) {
        registry.register(DgaModelBuilder)
    }

    object DgaModelBuilder : ToolingModelBuilder {

        override fun canBuild(modelName: String): Boolean {
            return modelName == DependencyModel::class.java.name
        }

        override fun buildAll(modelName: String, project: Project): DependencyModel {
            val projectName = project.name
            val projectModules = project.subprojects.map { ProjectModuleImpl(it.name) }.toSet()
            val moduleDependencies = extractModuleDependencies(project, projectModules)
            return DependencyModelImpl(projectName, projectModules, moduleDependencies)
        }

        private fun extractModuleDependencies(
            project: Project,
            modules: Set<ProjectModule>
        ): Map<String, List<ConfigurationModel>> = project.allprojects
            .associate { it.name to extractConfiguration(it.configurations, modules) }

        private fun extractConfiguration(
            configurationContainer: ConfigurationContainer,
            modules: Set<ProjectModule>
        ): List<ConfigurationModel> = configurationContainer
            .flatMap { configurationToDependencies(it, modules) }
            .toList()

        private fun configurationToDependencies(
            configuration: Configuration,
            modules: Set<ProjectModule>
        ): List<ConfigurationModel> =
            configuration.dependencies
                .map { DependencyImpl(it.name, it.group, it.version) }
                .filter { isDependencyWithinProjectModules(modules, it) }
                .map { ConfigurationModelData(configuration.name, it, ProjectModuleImpl(it.name)) }
                .toList()

        private fun isDependencyWithinProjectModules(
            modules: Set<ProjectModule>,
            dependency: Dependency
        ): Boolean =
            modules.map { it.projectModule }.contains(dependency.name)
    }
}
