package ch.addere.dga.app.domain.model

import ch.addere.dga.graph.application.DependencyService

class DependencyPrinter(
    private val dependencyService: DependencyService,
    private val printer: ConsolePrinter,
) {

    fun printToConsole() {
        val dependencies = dependencyService.configurationsWithOccurrence()
        if (dependencies.isNotEmpty()) {
            printer.println()
            val ordered = dependencies.toList().sortedByDescending { (_, value) -> value }.toMap()
            ordered.forEach { (configuration, amount) -> printer.println("${configuration.name}, $amount") }
        }
    }
}
