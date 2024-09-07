package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.model.CommandConfig
import ch.addere.dga.app.domain.service.printer.ConsolePrinter
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.importer.application.service.GradleProjectLoader

class DependencyCommandHandler(
    private val config: CommandConfig,
    private val printer: ConsolePrinter,
    private val gradleProjectLoader: GradleProjectLoader,
    private val projectOverviewPrinter: ProjectOverviewPrinter,
    private val filterService: FilterService,
    private val projectFilterPrinter: ProjectFilterPrinter,
    private val projectOutputPrinter: ProjectOutputPrinter,
) {

    fun run() {
        val projectName: String = gradleProjectLoader.loadGradleProject(config.gradleProjectPath)
        projectOverviewPrinter.output(projectName)

        val relevantDependencies: Set<Dependency> = if (config.hasActiveFilter) {
            val filteredDependencies: Set<Dependency> = filterService.filter(config.filterConfig)
            projectFilterPrinter.output(filteredDependencies)
            filteredDependencies
        } else {
            filterService.allDependencies()
        }

        if (config.hasActiveOutput) {
            projectOutputPrinter.output(config.outputConfig, relevantDependencies)
        }
        printer.println()
    }
}
