package ch.addere.dga.importer.domain.model

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Module
import java.io.File

class KotlinBuildFile(origin: Module, buildFile: File) : BuildFile(origin, buildFile) {

    override fun transformToModuleAndConfiguration(input: String): Pair<Module, Configuration> {
        val configuration = toConfiguration(input)
        val destinationModule = toDestinationModule(input)
        return destinationModule to configuration
    }

    private fun toDestinationModule(input: String): Module {
        val moduleString = Regex("\":[A-Za-z0-9\\-]+\"").findAll(input)
            .map { it.value }
            .map { it.replace(":", "").replace("\"", "") }
            .first()
        return Module(moduleString)
    }

    private fun toConfiguration(input: String): Configuration {
        val configuration = input.substringBefore("(")
            .replace("dependencies", "")
            .replace("{", "")
            .trim()
        return Configuration(configuration)
    }
}
