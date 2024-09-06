package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.model.OverviewData
import ch.addere.dga.core.domain.service.DependencyService
import ch.addere.dga.core.domain.service.ModuleService

class OverviewService(
    private val moduleService: ModuleService,
    private val dependencyService: DependencyService,
) {

    fun overviewData(projectName: String): OverviewData {
        val nofModules = moduleService.nofModules()
        val nofDependencies = dependencyService.nofProjectDependencies()
        val nofUniqueDependencies = dependencyService.nofUniqueConfigurations()
        return OverviewData(projectName, nofModules, nofDependencies, nofUniqueDependencies)
    }
}
