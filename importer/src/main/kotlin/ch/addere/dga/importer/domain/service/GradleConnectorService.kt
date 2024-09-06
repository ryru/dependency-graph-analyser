package ch.addere.dga.importer.domain.service

import ch.addere.dga.dependencymodel.DependencyModel
import java.io.File

interface GradleConnectorService {

    /**
     * Connect to a Gradle project and provide dependency data model.
     */
    fun connectToGradle(gradleProjectFolder: File): DependencyModel
}
