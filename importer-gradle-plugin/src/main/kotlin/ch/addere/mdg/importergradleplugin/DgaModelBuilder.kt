package ch.addere.mdg.importergradleplugin

import ch.addere.mdg.importergradlemodel.DependencyModel
import ch.addere.mdg.importergradlemodel.DependencyModelImpl
import org.gradle.api.Project
import org.gradle.tooling.provider.model.ToolingModelBuilder

class DgaModelBuilder : ToolingModelBuilder {

    override fun canBuild(modelName: String): Boolean {
        return modelName == DependencyModel::class.java.name
    }

    override fun buildAll(modelName: String, project: Project): DependencyModel {
        val projectName = extractProjectName(project)
        val projectModules = extractProjectModules(project)
        val moduleDependencies = extractModuleDependencies(project, projectModules)
        return DependencyModelImpl(projectName, projectModules, moduleDependencies)
    }
}
