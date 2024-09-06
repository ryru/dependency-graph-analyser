package ch.addere.dga.importer.application.service

import ch.addere.dga.dependencymodel.DependencyModel
import ch.addere.dga.importer.domain.service.GradleConnectorService
import ch.addere.dga.importer.domain.service.ModelImporterService
import java.io.File

class GradleProjectLoaderImpl(
    private val connectorService: GradleConnectorService,
    private val modelImporterService: ModelImporterService
) : GradleProjectLoader {

    override fun loadGradleProject(gradleProjectFolder: File): String {
        val dependencyModel: DependencyModel = connectorService.connectToGradle(gradleProjectFolder)
        return modelImporterService.import(dependencyModel)
    }
}
