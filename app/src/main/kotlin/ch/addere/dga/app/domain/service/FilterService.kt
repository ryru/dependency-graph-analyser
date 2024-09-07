package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.model.FilterConfig
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.FilteredConfiguration
import ch.addere.dga.core.domain.model.FilteredModules
import ch.addere.dga.core.domain.service.ConfigurationService
import ch.addere.dga.core.domain.service.DependencyRelationService
import ch.addere.dga.core.domain.service.DependencyService
import ch.addere.dga.core.domain.service.ModuleService

class FilterService(
    private val dependencyService: DependencyService,
    private val moduleService: ModuleService,
    private val configurationService: ConfigurationService,
    private val dependencyRelationService: DependencyRelationService,
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
        val inputModules = filterConfig.modules
        val inputOrigin = filterConfig.originModules
        val inputDestination = filterConfig.destinationModules
        val inputConfigurations = filterConfig.configurations

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

        if (filterConfig.includeTransitiveDependencies) {
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

        return filteredDependencies
    }
}
