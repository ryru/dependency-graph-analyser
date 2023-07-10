package ch.addere.mdg.importer.domain.model

import ch.addere.mdg.domain.model.Module
import ch.addere.mdg.importer.domain.model.GradleDsl.GROOVY
import ch.addere.mdg.importer.domain.model.GradleDsl.KOTLIN
import java.io.File
import java.util.*

class Project(rootPath: File) {

    val settingsFile: SettingsFile
    val buildFiles: Set<BuildFile>

    init {
        settingsFile = initialiseSettings(rootPath)
        buildFiles = initialiseBuildFiles(settingsFile)
    }

    private fun initialiseSettings(rootPath: File): SettingsFile {
        return if (rootPath.isFile and !isGradleSettingsFile(rootPath)) {
            throw IllegalArgumentException("file '$rootPath' seems not to be a Gradle project settings file")
        } else if (rootPath.isDirectory) {
            Optional.ofNullable(findFirstGradleSettingsFile(rootPath))
                .map(::SettingsFile)
                .orElseThrow { IllegalArgumentException("path '$rootPath' seems not to contain a Gradle project settings file") }
        } else {
            SettingsFile(rootPath)
        }
    }

    private fun findFirstGradleSettingsFile(rootPath: File) =
        rootPath.walk().filter(::isGradleSettingsFile).firstOrNull()

    private fun isGradleSettingsFile(input: File) =
        input.name.contains("settings.gradle")

    private fun initialiseBuildFiles(settingsFile: SettingsFile) =
        settingsFile.modules.map(::findBuildFile).toSet()

    private fun findBuildFile(module: Module): BuildFile {
        val rootDir = settingsFile.settingsFile.parentFile
        val moduleDir = rootDir.resolve(module.name)

        return Optional.ofNullable(findFirstGradleBuildFile(moduleDir))
            .or { Optional.ofNullable(findModuleGradleBuildFile(module, moduleDir)) }
            .map { buildFileOf(module, it) }
            .orElseThrow { IllegalArgumentException("path '$moduleDir' seems not to contain a Gradle build file") }
    }

    private fun findFirstGradleBuildFile(rootPath: File) =
        rootPath.walk().filter(::isGradleBuildFile).firstOrNull()

    private fun isGradleBuildFile(input: File) =
        input.name.contains("build.gradle")

    private fun findModuleGradleBuildFile(module: Module, rootPath: File) =
        rootPath.walk().filter { isModuleBuildFile(it, module) }.firstOrNull()

    private fun isModuleBuildFile(input: File, module: Module) =
        input.name.contains("${module.name}.gradle")

    private fun buildFileOf(module: Module, buildFile: File) =
        when (settingsFile.gradleDsl()) {
            GROOVY -> GroovyBuildFile(module, buildFile)
            KOTLIN -> KotlinBuildFile(module, buildFile)
        }
}
