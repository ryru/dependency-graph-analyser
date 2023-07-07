package ch.addere.mdg.domain.model

import ch.addere.mdg.domain.model.GradleDsl.GROOVY
import ch.addere.mdg.domain.model.GradleDsl.KOTLIN
import java.io.File

class SettingsFile(val settingsFile: File) {

    fun getModules(): Set<Module> {
        return settingsFile.bufferedReader().use {
            val includeBlock = it.lines()
                .dropWhile { line -> !line.matches(Regex("^include.?\\(.*")) }
                .takeWhile { line -> !line.matches(Regex("\\)")) }
                .flatMap { line -> transformToModules(line).stream() }
                .toList()
            includeBlock
        }.toSet()
    }

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
