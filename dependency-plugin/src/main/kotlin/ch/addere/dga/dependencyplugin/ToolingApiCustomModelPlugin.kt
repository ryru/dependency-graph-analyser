package ch.addere.dga.dependencyplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.tooling.provider.model.ToolingModelBuilderRegistry
import javax.inject.Inject

class ToolingApiCustomModelPlugin @Inject constructor(private val registry: ToolingModelBuilderRegistry) :
    Plugin<Project> {

    override fun apply(target: Project) {
        registry.register(DgaModelBuilder())
    }
}
