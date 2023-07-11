package ch.addere.mdg.app.domain.model

import ch.addere.mdg.graph.domain.service.DependencyService
import ch.addere.mdg.importer.domain.model.Project

class OverviewPrinter(
    private val printer: ConsolePrinter,
    private val project: Project,
    private val service: DependencyService
) {

    fun printToConsole() {
        val analysedFileName = project.settingsFile.settingsFile.name
        val nofModules = service.allModules().size
        val nofDependencies = service.allDependencies().size
        val nofUniqueDependencies =
            service.allDependencies().map { it.configuration.name }.toSet().size

        printer.println("Analyse $analysedFileName")
        printer.println(String.format("%6d modules", nofModules))
        printer.println(
            String.format(
                "%6d dependencies (%d unique)",
                nofDependencies,
                nofUniqueDependencies
            )
        )
    }
}
