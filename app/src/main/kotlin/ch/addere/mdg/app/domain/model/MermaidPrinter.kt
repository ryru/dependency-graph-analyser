package ch.addere.mdg.app.domain.model

import ch.addere.mdg.exporter.domain.model.exporter.mermaid.MermaidFullGraphExporter
import ch.addere.mdg.exporter.domain.model.writer.ConsoleWriter
import ch.addere.mdg.graph.domain.service.DependencyService

class MermaidPrinter(

    private val service: DependencyService,
    private val printer: ConsolePrinter,
) {

    fun printToConsole() {
        printer.println()
        val mermaidFullGraphExporter = MermaidFullGraphExporter(service.allDependencies())
        mermaidFullGraphExporter.print(ConsoleWriter(printer.printer))
    }
}
