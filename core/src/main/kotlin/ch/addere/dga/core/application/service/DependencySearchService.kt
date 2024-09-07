package ch.addere.dga.core.application.service

import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module

interface DependencySearchService {

    /**
     * Returns all dependencies that have modules requiring [module].
     */
    fun findAllDependenciesDependingOnModule(
        module: Module,
        withConfigurations: Collection<Configuration>
    ): Set<Dependency>

    /**
     * Returns all dependencies which are required in order [module] can work.
     */
    fun findAllDependenciesUsedByModule(
        module: Module,
        withConfigurations: Collection<Configuration>
    ): Set<Dependency>
}
