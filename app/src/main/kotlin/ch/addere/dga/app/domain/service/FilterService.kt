package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.model.FilterConfig
import ch.addere.dga.core.application.service.DependencySearchService
import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.FilteredConfiguration
import ch.addere.dga.core.domain.model.FilteredModules
import ch.addere.dga.core.domain.model.Module
import ch.addere.dga.core.domain.service.ConfigurationService
import ch.addere.dga.core.domain.service.DependencyService
import ch.addere.dga.core.domain.service.ModuleService

class FilterService(
    private val dependencyService: DependencyService,
    private val moduleService: ModuleService,
    private val configurationService: ConfigurationService,
    private val dependencySearchService: DependencySearchService,
) {

    fun allDependencies(): Set<Dependency> {
        return dependencyService.filteredDependencies(
            FilteredModules(false, emptyList()),
            FilteredModules(false, emptyList()),
            FilteredModules(false, emptyList()),
            FilteredConfiguration(false, emptyList())
        )
    }

    fun filter(filterConfig: FilterConfig): Set<Dependency> {
        val requestedModules = filterConfig.modules
        val requestedOriginModules = filterConfig.originModules
        val requestedDestinationModules = filterConfig.destinationModules
        val requestedConfigurations = filterConfig.configurations

        val requestedCanonicalModules =
            requestedModules.flatMap(moduleService::resolvePartialModuleName).toList()
        val requestedCanonicalOriginModules =
            requestedOriginModules.flatMap(moduleService::resolvePartialModuleName).toList()
        val requestedCanonicalDestinationModules =
            requestedDestinationModules.flatMap(moduleService::resolvePartialModuleName).toList()
        val requestedCanonicalConfigurations: List<Configuration> =
            requestedConfigurations.flatMap(configurationService::resolvePartialConfigurationName)
                .toList()

        var filteredDependencies: Set<Dependency> =
            dependencyService.filteredDependencies(
                FilteredModules(requestedModules.isNotEmpty(), requestedCanonicalModules),
                FilteredModules(
                    requestedOriginModules.isNotEmpty(),
                    requestedCanonicalOriginModules
                ),
                FilteredModules(
                    requestedDestinationModules.isNotEmpty(),
                    requestedCanonicalDestinationModules
                ),
                FilteredConfiguration(
                    requestedConfigurations.isNotEmpty(),
                    requestedCanonicalConfigurations
                )
            )

        if (filterConfig.includeTransitiveDependencies) {
            val relevantModules: Set<Module> =
                filteredDependencies.flatMap { setOf(it.origin, it.destination) }.toSet()
            val relevantConfigs: Set<Configuration> =
                filteredDependencies.map { it.configuration }.toSet()

            val transitiveDependencies: Set<Dependency> = relevantModules.flatMap {
                dependencySearchService.findAllDependenciesUsedByModule(
                    it,
                    relevantConfigs
                )
            }.toSet()

            filteredDependencies = filteredDependencies union transitiveDependencies
        }

        return filteredDependencies
    }
}
