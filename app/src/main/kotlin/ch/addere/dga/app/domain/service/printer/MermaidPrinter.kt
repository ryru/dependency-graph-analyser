package ch.addere.dga.app.domain.service.printer

import ch.addere.dga.app.domain.model.mermaid.MermaidFullGraphExporter
import ch.addere.dga.app.domain.model.writer.ConsoleWriter
import ch.addere.dga.core.domain.model.Dependency

class MermaidPrinter(private val printer: ConsolePrinter) {

    fun printToConsole(dependenciesForOutput: Set<Dependency>) {
        printer.println()
        val mermaidFullGraphExporter = MermaidFullGraphExporter(dependenciesForOutput.toSortedSet())
        mermaidFullGraphExporter.print(ConsoleWriter(printer.printer))
    }
}
