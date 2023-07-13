package ch.addere.mdg.graph.application.module

import ch.addere.mdg.graph.domain.service.DependencyRepository

class DependencyServiceImpl(private val dependencyRepository: DependencyRepository) :
    DependencyService {

    override fun nofDependencies(): Int {
        return dependencyRepository.getAllDependencies().size
    }

    override fun nofUniqueDependencies(): Int {
        return dependencyRepository.getAllDependencies().map { it.configuration.name }.toSet().size
    }
}
