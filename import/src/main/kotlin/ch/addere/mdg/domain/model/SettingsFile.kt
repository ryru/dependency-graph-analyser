package ch.addere.mdg.domain.model

import ch.addere.mdg.domain.model.extractor.findModulesInGroovySettings
import ch.addere.mdg.domain.model.extractor.findModulesInKotlinSettings
import java.io.File

class SettingsFile(private val settingsFile: File) {

    fun getModules(): Set<Module> {
        return if (settingsFile.endsWith("kts")) {
            toModules(::findModulesInKotlinSettings)
        } else {
            toModules(::findModulesInGroovySettings)
        }
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
