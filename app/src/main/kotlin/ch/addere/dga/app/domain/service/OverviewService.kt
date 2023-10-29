package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.model.OverviewData
import ch.addere.dga.graph.application.DependencyService
import ch.addere.dga.graph.application.ModuleService
import ch.addere.dga.importer.domain.model.Project

class OverviewService(
    private val project: Project,
    private val moduleService: ModuleService,
    private val dependencyService: DependencyService,
) {

    fun overviewData(): OverviewData {
        val name = project.projectName()
        val nofModules = moduleService.nofModules()
        val nofDependencies = dependencyService.nofProjectDependencies()
        val nofUniqueDependencies = dependencyService.nofUniqueConfigurations()
        return OverviewData(name, nofModules, nofDependencies, nofUniqueDependencies)
    }
}
