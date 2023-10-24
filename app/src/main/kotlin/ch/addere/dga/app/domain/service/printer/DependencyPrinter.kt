package ch.addere.dga.app.domain.service.printer

import ch.addere.dga.graph.domain.model.Configuration

class DependencyPrinter(private val printer: ConsolePrinter) {

    fun printToConsole(configurationsForOutput: Map<Configuration, Int>) {
        if (configurationsForOutput.isNotEmpty()) {
            printer.println()
            val ordered =
                configurationsForOutput.toList().sortedByDescending { (_, value) -> value }.toMap()
            ordered.forEach { (configuration, amount) -> printer.println("${configuration.name}, $amount") }
        }
    }
}
