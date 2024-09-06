package ch.addere.dga.app.domain.model.mermaid

import ch.addere.dga.app.domain.model.writer.Writer
import ch.addere.dga.core.domain.model.Dependency
import java.util.*

class MermaidPartialGraphExporter(
    private val direct: SortedSet<Dependency>,
    private val indirect: SortedSet<Dependency>
) : MermaidGraphExporter() {

    override fun print(writer: Writer) {
        if (direct.isNotEmpty() or indirect.isNotEmpty()) {
            writer.writeln("graph TD")
        }

        if (direct.isNotEmpty()) {
            printDirect(writer)
        }

        if (indirect.isNotEmpty()) {
            printIndirect(writer)
        }
    }

    private fun printDirect(writer: Writer) {
        direct.forEach {
            writer.writeln(
                INDENTATION,
                directRelation(it.origin, it.destination, it.configuration)
            )
        }
    }

    private fun printIndirect(writer: Writer) {
        indirect.forEach {
            writer.writeln(
                INDENTATION,
                indirectRelation(it.origin, it.destination, it.configuration)
            )
        }
    }
}
