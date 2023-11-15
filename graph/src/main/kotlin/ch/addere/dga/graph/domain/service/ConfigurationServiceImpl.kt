package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency

class ConfigurationServiceImpl(private val dependencyRepository: DependencyRepository) :
    ConfigurationService {

    override fun resolvePartialConfigurationName(configuration: Configuration): Set<Configuration> {
        val normalisedConfigurationName = configuration.name.replace("\\*+".toRegex(), ".*")
        return recLookup(dependencyRepository.getAllDependencies()
            .map { (_, _, config) -> config }
            .toSet(), normalisedConfigurationName)
    }

    private fun recLookup(
        configurations: Set<Configuration>,
        nameToLookup: String
    ): Set<Configuration> {
        return when {
            configurations.isEmpty() -> emptySet()
            '*' !in nameToLookup -> lookupName(nameToLookup)
            else -> regexLookup(nameToLookup.toRegex())
        }
    }

    private fun lookupName(lookupName: String): Set<Configuration> {
        return filterConfigurations { it.configuration.name == lookupName }
    }

    private fun regexLookup(regexNameToLookup: Regex): Set<Configuration> {
        return filterConfigurations { it.configuration.name.matches(regexNameToLookup) }
    }

    private fun filterConfigurations(predicate: (Dependency) -> Boolean): Set<Configuration> {
        return dependencyRepository.getAllDependencies()
            .filter(predicate)
            .map { it.configuration }
            .toSet()
    }
}
