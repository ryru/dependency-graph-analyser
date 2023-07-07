package ch.addere.mdg.domain.model

import ch.addere.mdg.domain.model.GradleDsl.GROOVY
import ch.addere.mdg.domain.model.GradleDsl.KOTLIN
import ch.addere.mdg.domain.model.extractor.findModulesInGroovySettings
import ch.addere.mdg.domain.model.extractor.findModulesInKotlinSettings
import java.io.File

class SettingsFile(val settingsFile: File) {

    fun getModules(): Set<Module> {
        return when (gradleDsl()) {
            KOTLIN -> toModules(::findModulesInKotlinSettings)
            GROOVY -> toModules(::findModulesInGroovySettings)
        }
    }

    fun gradleDsl(): GradleDsl = if (settingsFile.name.endsWith("kts")) {
        KOTLIN
    } else {
        GROOVY
    }

    private fun toModules(transform: (String) -> Set<Module>): Set<Module> {
        val potentialModules = settingsFile.bufferedReader().use {
            val includeBlock = it.lines()
                .dropWhile { line -> !line.matches(Regex("^include.*")) }
                .toList()
            includeBlock
        }

        return transform(potentialModules.joinToString())
    }
}
