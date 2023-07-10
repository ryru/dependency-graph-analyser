package ch.addere.mdg.exporter.domain.model.exporter.mermaid

import ch.addere.mdg.exporter.domain.model.Exporter
import ch.addere.mdg.graph.domain.model.Configuration
import ch.addere.mdg.graph.domain.model.Module
import java.math.BigInteger
import java.security.MessageDigest

const val INDENTATION = 4

private const val RELATION_DIRECT = "-->"
private const val RELATION_INDIRECT = "-.->"

abstract class MermaidGraphExporter : Exporter {

    private val md = MessageDigest.getInstance("MD5")

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
        val key = generateReproducibleKeyWithoutRegisteredWords(module)
        val value = module.name
        return "$key($value)"
    }

    private fun generateReproducibleKeyWithoutRegisteredWords(module: Module) =
        "v" + BigInteger(1, md.digest(module.name.toByteArray())).toString(16).take(6)

    private fun toDependencyRelation(configuration: Configuration): String {
        return "|${configuration.name}|"
    }
}
