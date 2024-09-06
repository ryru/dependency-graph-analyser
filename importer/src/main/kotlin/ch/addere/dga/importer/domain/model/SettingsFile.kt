package ch.addere.dga.importer.domain.model

import ch.addere.dga.core.domain.model.Module
import ch.addere.dga.importer.domain.model.GradleDsl.GROOVY
import ch.addere.dga.importer.domain.model.GradleDsl.KOTLIN
import java.io.BufferedReader
import java.io.File
import kotlin.streams.asStream

class SettingsFile(val settingsFile: File) {

    val modules: Set<Module>

    init {
        modules = includeBlockToModule()
            .ifEmpty(::heuristicToModule)
            .ifEmpty { throw IllegalStateException("unable to find modules in '$settingsFile'") }
    }

    private fun includeBlockToModule(): Set<Module> {
        return settingsFile.bufferedReader().use { findIncludeBlock(it) }.toSet()
    }

    private fun findIncludeBlock(reader: BufferedReader): List<Module> =
        reader.lines()
            .dropWhile { line -> !line.matches(Regex("^include.?\\(.*")) }
            .takeWhile { line -> !line.matches(Regex("\\)")) }
            .flatMap { line -> transformToModules(line).stream() }
            .toList()

    private fun heuristicToModule(): Set<Module> {
        return settingsFile.bufferedReader().use { findHeuristically(it) }.toSet()
    }

    private fun findHeuristically(reader: BufferedReader): List<Module> =
        reader.lines()
            .dropWhile { line -> !line.matches(Regex("(val|var)?.?(module|modules).*")) }
            .flatMap { line -> Regex("([\"'])[A-Za-z0-9\\-]+[\"'],?").findAll(line).asStream() }
            .flatMap { regexMatch -> transformToModules(regexMatch.value).stream() }
            .toList()

    fun gradleDsl(): GradleDsl = if (settingsFile.name.endsWith("kts")) {
        KOTLIN
    } else {
        GROOVY
    }

    private fun transformToModules(input: String): Set<Module> {
        val stringOfModules = removeSyntaxClutter(input)
        val listOfModules = splitToModuleNames(stringOfModules)
        return listOfModules.map(::Module).toSet()
    }

    private fun removeSyntaxClutter(input: String): String {
        return input
            .replace("\n", "")
            .replace("include", "")
            .replace("\"", "")
            .replace("'", "")
            .replace("(", "")
            .replace(")", "")
    }

    private fun splitToModuleNames(input: String): List<String> {
        return input.split(",").map(String::trim).filter(String::isNotBlank)
    }
}
