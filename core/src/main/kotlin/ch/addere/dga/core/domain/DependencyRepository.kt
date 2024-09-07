package ch.addere.dga.core.domain

import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module

interface DependencyRepository {
    fun addDependency(dependency: Dependency)
    fun addDependency(dependencies: Collection<Dependency>)
    fun getDependencyByOrigin(module: Module): Set<Dependency>
    fun getDependencyByConfiguration(configuration: Configuration): Set<Dependency>
    fun getDependencyByDestination(module: Module): Set<Dependency>
    fun getAllDependencies(): Set<Dependency>
}
