package ch.addere.dga.app.domain.service

import ch.addere.dga.app.configuration.OutputOptions.OutputOptionConfigurations
import ch.addere.dga.app.configuration.OutputOptions.OutputOptionMermaid
import ch.addere.dga.app.configuration.OutputOptions.OutputOptionModules
import ch.addere.dga.app.configuration.OutputOptions.OutputOptionOverviewOnly
import ch.addere.dga.app.domain.model.CommandConfig
import ch.addere.dga.app.domain.service.printer.ConsolePrinter
import ch.addere.dga.app.domain.service.printer.DependencyPrinter
import ch.addere.dga.app.domain.service.printer.MermaidPrinter
import ch.addere.dga.app.domain.service.printer.ModulePrinter
import ch.addere.dga.app.domain.service.printer.OverviewPrinter
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.FilteredConfiguration
import ch.addere.dga.core.domain.model.FilteredModules
import ch.addere.dga.core.domain.service.ConfigurationService
import ch.addere.dga.core.domain.service.DependencyRelationService
import ch.addere.dga.core.domain.service.DependencyService
import ch.addere.dga.core.domain.service.ModuleService
import ch.addere.dga.importer.application.service.GradleProjectLoader

class DependencyCommand(
    private val config: CommandConfig,
    private val dependencies: DependencyPrinter,
    private val mermaid: MermaidPrinter,
    private val modulePrinter: ModulePrinter,
    private val overview: OverviewPrinter,
    private val printer: ConsolePrinter,
    private val dependencyService: DependencyService,
    private val overviewService: OverviewService,
    private val moduleService: ModuleService,
    private val configurationService: ConfigurationService,
    private val dependencyRelationService: DependencyRelationService,
    private val gradleProjectLoader: GradleProjectLoader
) {

    fun run() {

        val projectName: String = gradleProjectLoader.loadGradleProject(config.gradleProjectPath)

        val overviewDataForOutput = overviewService.overviewData(projectName)

        val inputModules = config.filterConfig.modules
        val inputOrigin = config.filterConfig.originModules
        val inputDestination = config.filterConfig.destinationModules
        val inputConfigurations = config.filterConfig.configurations

        val filteredModules = inputModules.flatMap(moduleService::resolvePartialModuleName).toList()
        val filteredOrigin = inputOrigin.flatMap(moduleService::resolvePartialModuleName).toList()
        val filteredDestination =
            inputDestination.flatMap(moduleService::resolvePartialModuleName).toList()
        val filteredConfigurations =
            inputConfigurations.flatMap(configurationService::resolvePartialConfigurationName)
                .toList()

        var filteredDependencies: Set<Dependency> =
            dependencyService.filteredDependencies(
                FilteredModules(inputModules.isNotEmpty(), filteredModules),
                FilteredModules(inputOrigin.isNotEmpty(), filteredOrigin),
                FilteredModules(inputDestination.isNotEmpty(), filteredDestination),
                FilteredConfiguration(inputConfigurations.isNotEmpty(), filteredConfigurations)
            )

        if (config.filterConfig.isTransitiveModules) {
            val configurationSet = filteredDependencies.map { it.configuration }.toSet()
            filteredDependencies = filteredDependencies.flatMap { dependency ->
                dependencyRelationService.allDependenciesOf(
                    dependency.origin,
                    configurationSet
                ) union
                    dependencyRelationService.allDependenciesOf(
                        dependency.destination,
                        configurationSet
                    )
            }.toSet()
        }

        printer.println()
        when (config.outputConfig) {
            OutputOptionOverviewOnly -> overview.printToConsole(overviewDataForOutput)
            OutputOptionModules -> modulePrinter.printToConsole(extractModules(filteredDependencies))
            OutputOptionConfigurations -> dependencies.printToConsole(
                configurationAndCount(
                    filteredDependencies
                )
            )

            OutputOptionMermaid -> mermaid.printToConsole(filteredDependencies)
        }
        printer.println()
    }
}
