package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.model.CommandArgument
import ch.addere.dga.app.domain.service.printer.ConsolePrinter
import ch.addere.dga.app.domain.service.printer.DependencyPrinter
import ch.addere.dga.app.domain.service.printer.MermaidPrinter
import ch.addere.dga.app.domain.service.printer.ModulePrinter
import ch.addere.dga.app.domain.service.printer.OverviewPrinter
import ch.addere.dga.graph.application.DependencyService
import ch.addere.dga.graph.application.ModuleService
import ch.addere.dga.graph.domain.service.DependencyRepository

class DependencyCommand(
    private val argument: CommandArgument,
    private val dependencies: DependencyPrinter,
    private val mermaid: MermaidPrinter,
    private val modules: ModulePrinter,
    private val overview: OverviewPrinter,
    private val printer: ConsolePrinter,
    private val moduleService: ModuleService,
    private val dependencyService: DependencyService,
    private val repository: DependencyRepository,
    private val overviewService: OverviewService,
) {

    fun run() {

        val overviewDataForOutput = overviewService.overviewData()
        val modulesForOutput = moduleService.modules()
        val configurationsForOutput = dependencyService.configurationsWithOccurrence()
        val dependenciesForOutput = repository.getAllDependencies()

        printer.println()
        overview.printToConsole(overviewDataForOutput)

        if (argument.optionsOutput.isAllModules) {
            modules.printToConsole(modulesForOutput)
        } else if (argument.optionsOutput.isAllConfigurations) {
            dependencies.printToConsole(configurationsForOutput)
        } else if (argument.optionsOutput.isChartMermaid) {
            mermaid.printToConsole(dependenciesForOutput)
        }
        printer.println()
    }
}
