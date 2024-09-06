package ch.addere.dga.importer.application.service

import java.io.File

interface GradleProjectLoader {

    /**
     * Load the Gradle project, analyse it and return the root project name.
     */
    fun loadGradleProject(gradleProjectFolder: File): String
}
