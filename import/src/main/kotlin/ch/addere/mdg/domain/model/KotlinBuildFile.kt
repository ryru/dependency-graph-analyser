package ch.addere.mdg.domain.model

import java.io.File

class KotlinBuildFile(origin: Module, buildFile: File) : BuildFile(origin, buildFile) {

    override fun transformToModuleAndConfiguration(input: String): Pair<Module, Configuration> {
        val configuration = toConfiguration(input)
        val destinationModule = toDestinationModule(input)
        return destinationModule to configuration
    }

    private fun toDestinationModule(input: String): Module {
        val module = input.substringAfter(":")
            .replace(")", "")
            .replace("\"", "")
            .replace("{", "")
            .replace("}", "")
            .trim()
        return Module(module)
    }

    private fun toConfiguration(input: String): Configuration {
        val configuration = input.substringBefore("(")
            .replace("dependencies", "")
            .replace("{", "")
            .trim()
        return Configuration(configuration)
    }
}
