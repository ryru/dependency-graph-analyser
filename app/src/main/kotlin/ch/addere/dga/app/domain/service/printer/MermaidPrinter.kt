package ch.addere.dga.app.domain.service.printer

import ch.addere.dga.exporter.domain.model.exporter.mermaid.MermaidFullGraphExporter
import ch.addere.dga.exporter.domain.model.writer.ConsoleWriter
import ch.addere.dga.graph.domain.model.Dependency

class MermaidPrinter(private val printer: ConsolePrinter) {

    fun printToConsole(dependenciesForOutput: Set<Dependency>) {
        printer.println()
        val mermaidFullGraphExporter = MermaidFullGraphExporter(dependenciesForOutput.toSortedSet())
        mermaidFullGraphExporter.print(ConsoleWriter(printer.printer))
    }
}
