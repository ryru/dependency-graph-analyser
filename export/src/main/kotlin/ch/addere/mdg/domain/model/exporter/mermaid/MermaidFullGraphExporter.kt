package ch.addere.mdg.domain.model.exporter.mermaid

import ch.addere.mdg.domain.model.Configuration
import ch.addere.mdg.domain.model.Dependency
import ch.addere.mdg.domain.model.Module
import ch.addere.mdg.domain.model.writer.Writer
import java.util.*

class MermaidFullGraphExporter(private val dependencies: SortedSet<Dependency>) :
    MermaidGraphExporter() {

    override fun print(writer: Writer) {
        if (dependencies.isNotEmpty()) {
            printSetTo(writer)
        }
    }

    private fun printSetTo(writer: Writer) {
        writer.writeln("graph TD")
        dependencies.forEach {
            val line =
                module(it.origin) + " -->|" + dependencies(it.configuration) + "| " + module(it.destination)
            writer.writeln(INDENTATION, line)
        }
    }

    private fun module(module: Module): String {
        val key = module.name.replace("-", "_")
        val value = module.name
        return "$key($value)"
    }

    private fun dependencies(configuration: Configuration): String {
        return configuration.name
    }
}
