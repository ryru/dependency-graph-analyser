package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.service.printer.ConsolePrinter
import ch.addere.dga.core.domain.service.DependencyService
import ch.addere.dga.core.domain.service.ModuleService

class ProjectOverviewPrinter(
    private val printer: ConsolePrinter,
    private val moduleService: ModuleService,
    private val dependencyService: DependencyService,
) {

    /**
     * Print common information about the whole analysed Gradle project.
     */
    fun output(projectName: String) {
        val overviewData = overviewData(projectName)
        printer.println()
        printer.println("Analyse project \"${overviewData.projectName}\"")
        printer.println(String.format("%6d modules", overviewData.nofModules))
        printer.println(
            String.format(
                "%6d dependency configurations (%d unique dependency configurations)",
                overviewData.nofDependencies,
                overviewData.nofUniqueDependencies
            )
        )
    }

    private fun overviewData(projectName: String): OverviewData {
        val nofModules = moduleService.nofModules()
        val nofDependencies = dependencyService.nofProjectDependencies()
        val nofUniqueDependencies = dependencyService.nofUniqueConfigurations()
        return OverviewData(projectName, nofModules, nofDependencies, nofUniqueDependencies)
    }

    private data class OverviewData(
        val projectName: String,
        val nofModules: Int,
        val nofDependencies: Int,
        val nofUniqueDependencies: Int
    )
}
