package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.model.CommandConfig
import ch.addere.dga.app.domain.service.printer.ConsolePrinter
import ch.addere.dga.app.domain.service.printer.DependencyPrinter
import ch.addere.dga.app.domain.service.printer.MermaidPrinter
import ch.addere.dga.app.domain.service.printer.ModulePrinter
import ch.addere.dga.app.domain.service.printer.OverviewPrinter
import ch.addere.dga.graph.domain.service.DependencyService
import ch.addere.dga.graph.domain.service.ModuleService

class DependencyCommand(
    private val config: CommandConfig,
    private val dependencies: DependencyPrinter,
    private val mermaid: MermaidPrinter,
    private val modulePrinter: ModulePrinter,
    private val overview: OverviewPrinter,
    private val printer: ConsolePrinter,
    private val dependencyService: DependencyService,
    private val overviewService: OverviewService,
    private val moduleService: ModuleService
) {

    fun run() {

        val overviewDataForOutput = overviewService.overviewData()

        val modules =
            config.filterConfig.modules.flatMap(moduleService::resolvePartialModuleName).toList()
        val originModules =
            config.filterConfig.originModules.flatMap(moduleService::resolvePartialModuleName)
                .toList()
        val destinationModules =
            config.filterConfig.destinationModules.flatMap(moduleService::resolvePartialModuleName)
                .toList()
        val configurations = config.filterConfig.configurations

        val filteredDependencies =
            dependencyService.filteredDependencies(
                modules,
                originModules,
                destinationModules,
                configurations
            )

        printer.println()
        overview.printToConsole(overviewDataForOutput)

        if (config.outputConfig.isAllModule) {
            val uniqueModules = extractModules(filteredDependencies)
            modulePrinter.printToConsole(uniqueModules)
        } else if (config.outputConfig.isAllConfigurations) {
            val countedConfigurations = configurationAndCount(filteredDependencies)
            dependencies.printToConsole(countedConfigurations)
        } else if (config.outputConfig.isChartMermaid) {
            mermaid.printToConsole(filteredDependencies)
        }
        printer.println()
    }
}
