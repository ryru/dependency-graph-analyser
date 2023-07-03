package ch.addere.mdg.domain.model.exporter.mermaid

import ch.addere.mdg.domain.model.Configuration
import ch.addere.mdg.domain.model.Module
import ch.addere.mdg.domain.model.exporter.Exporter

const val INDENTATION = 4

private const val RELATION_DIRECT = "-->"
private const val RELATION_INDIRECT = "-.->"

abstract class MermaidGraphExporter : Exporter {

    fun directRelation(origin: Module, destination: Module, configuration: Configuration): String {
        return relation(origin, destination, configuration, RELATION_DIRECT)
    }

    fun indirectRelation(
        origin: Module,
        destination: Module,
        configuration: Configuration
    ): String {
        return relation(origin, destination, configuration, RELATION_INDIRECT)
    }

    private fun relation(
        origin: Module,
        destination: Module,
        configuration: Configuration,
        relation: String
    ): String {
        val originModule = toKeyValueModule(origin)
        val destinationModule = toKeyValueModule(destination)
        val dependenciesRelation = toDependencyRelation(configuration)
        return "$originModule $relation$dependenciesRelation $destinationModule"
    }

    private fun toKeyValueModule(module: Module): String {
        val key = module.name.replace("-", "_")
        val value = module.name
        return "$key($value)"
    }

    private fun toDependencyRelation(configuration: Configuration): String {
        return "|${configuration.name}|"
    }
}
