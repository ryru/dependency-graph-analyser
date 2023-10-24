package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.model.CommandConfig
import ch.addere.dga.app.domain.service.printer.ConsolePrinter
import ch.addere.dga.app.domain.service.printer.DependencyPrinter
import ch.addere.dga.app.domain.service.printer.MermaidPrinter
import ch.addere.dga.app.domain.service.printer.ModulePrinter
import ch.addere.dga.app.domain.service.printer.OverviewPrinter
import ch.addere.dga.graph.application.DependencyService
import ch.addere.dga.graph.application.ModuleFilter
import ch.addere.dga.graph.application.ModuleService

class DependencyCommand(
    private val config: CommandConfig,
    private val dependencies: DependencyPrinter,
    private val mermaid: MermaidPrinter,
    private val modules: ModulePrinter,
    private val overview: OverviewPrinter,
    private val printer: ConsolePrinter,
    private val moduleService: ModuleService,
    private val dependencyService: DependencyService,
    private val overviewService: OverviewService,
) {

    fun run() {

        val filterContainsModule = if (config.filterConfig.modules == null) {
            ModuleFilter { _ -> true }
        } else {
            ModuleFilter { module -> config.filterConfig.modules.contains(module) }
        }

        val overviewDataForOutput = overviewService.overviewData()
        val modulesForOutput = moduleService.modules(filterContainsModule)
        val configurationsForOutput = dependencyService.configurationsWithOccurrence()
        val dependenciesForOutput = dependencyService.dependencies(filterContainsModule)

        printer.println()
        overview.printToConsole(overviewDataForOutput)

        if (config.outputConfig.isAllModule) {
            modules.printToConsole(modulesForOutput)
        } else if (config.outputConfig.isAllConfigurations) {
            dependencies.printToConsole(configurationsForOutput)
        } else if (config.outputConfig.isChartMermaid) {
            mermaid.printToConsole(dependenciesForOutput)
        }
        printer.println()
    }
}
