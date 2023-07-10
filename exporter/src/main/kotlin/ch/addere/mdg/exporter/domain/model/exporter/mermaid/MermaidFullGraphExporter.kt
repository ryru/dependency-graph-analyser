package ch.addere.mdg.exporter.domain.model.exporter.mermaid

import ch.addere.mdg.exporter.domain.model.writer.Writer
import ch.addere.mdg.graph.domain.model.Dependency
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
            writer.writeln(
                INDENTATION,
                directRelation(it.origin, it.destination, it.configuration)
            )
        }
    }
}
