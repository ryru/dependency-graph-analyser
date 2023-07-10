package ch.addere.mdg.importer.domain.model

import ch.addere.mdg.graph.domain.model.Configuration
import ch.addere.mdg.graph.domain.model.Module
import java.io.File

class GroovyBuildFile(origin: Module, buildFile: File) : BuildFile(origin, buildFile) {

    override fun transformToModuleAndConfiguration(input: String): Pair<Module, Configuration> {
        val configuration = toConfiguration(input)
        val destinationModule = toDestinationModule(input)
        return destinationModule to configuration
    }

    private fun toDestinationModule(input: String): Module {
        val moduleString = Regex("':[A-Za-z0-9\\-]+'").findAll(input)
            .map { it.value }
            .map { it.replace(":", "").replace("'", "") }
            .first()
        return Module(moduleString)
    }

    private fun toConfiguration(input: String): Configuration {
        val configuration = input.substringBefore("project")
            .replace("dependencies", "")
            .replace("{", "")
            .trim()
        return Configuration(configuration)
    }
}
