package ch.addere.dga.graph.application

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.service.DependencyRepository

class DependencyServiceImpl(private val dependencyRepository: DependencyRepository) :
    DependencyService {

    override fun nofDependencies(): Int {
        return dependencyRepository.getAllDependencies().size
    }

    override fun nofUniqueDependencies(): Int {
        return dependencyRepository.getAllDependencies().map { it.configuration.name }.toSet().size
    }

    override fun configuraitonsWithOccurence(): Map<Configuration, Int> {
        return dependencyRepository.getAllDependencies().associate { dependency ->
            dependency.configuration to dependencyRepository.getAllDependencies()
                .count { it == dependency }
        }
    }
}
