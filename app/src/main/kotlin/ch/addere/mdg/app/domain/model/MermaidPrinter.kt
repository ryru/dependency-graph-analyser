package ch.addere.mdg.app.domain.model

import ch.addere.mdg.exporter.domain.model.exporter.mermaid.MermaidFullGraphExporter
import ch.addere.mdg.exporter.domain.model.writer.ConsoleWriter
import ch.addere.mdg.graph.domain.service.DependencyRepository

class MermaidPrinter(
    private val printer: ConsolePrinter,
    private val repository: DependencyRepository,
) {

    fun printToConsole() {
        printer.println()
        val mermaidFullGraphExporter = MermaidFullGraphExporter(repository.getAllDependencies())
        mermaidFullGraphExporter.print(ConsoleWriter(printer.printer))
    }
}
