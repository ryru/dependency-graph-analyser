package ch.addere.mdg.app.domain.model

import ch.addere.mdg.graph.application.DependencyService
import ch.addere.mdg.graph.application.ModuleService
import ch.addere.mdg.importer.domain.model.Project

class OverviewPrinter(
    private val dependencyService: DependencyService,
    private val moduleService: ModuleService,
    private val printer: ConsolePrinter,
    private val project: Project
) {

    fun printToConsole() {
        val analysedFileName = project.settingsFile.settingsFile.name
        val nofModules = moduleService.nofModules()
        val nofDependencies = dependencyService.nofDependencies()
        val nofUniqueDependencies = dependencyService.nofUniqueDependencies()

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
