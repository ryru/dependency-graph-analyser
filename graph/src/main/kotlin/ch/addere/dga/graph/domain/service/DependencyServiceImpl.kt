package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.application.DependencyService
import ch.addere.dga.graph.domain.model.Configuration

class DependencyServiceImpl(private val dependencyRepository: DependencyRepository) :
    DependencyService {

    override fun nofDependencies(): Int {
        return dependencyRepository.getAllDependencies().size
    }

    override fun nofUniqueDependencies(): Int {
        return dependencyRepository.getAllDependencies().map { it.configuration.name }.toSet().size
    }

    override fun configurationsWithOccurrence(): Map<Configuration, Int> {
        return dependencyRepository.getAllDependencies()
            .associate { it.configuration to countNofConfigurations(it.configuration) }
    }

    private fun countNofConfigurations(configuration: Configuration): Int {
        return dependencyRepository.getDependencyByConfiguration(configuration).count()
    }
}
