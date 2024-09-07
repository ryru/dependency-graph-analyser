package ch.addere.dga.core.domain

import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module
import java.util.*

class DependencyRepositoryImpl : DependencyRepository {

    private val dependencies = mutableSetOf<Dependency>()

    override fun addDependency(dependency: Dependency) {
        dependencies.add(dependency)
    }

    override fun addDependency(dependencies: Collection<Dependency>) {
        this.dependencies.addAll(dependencies)
    }

    override fun getDependencyByOrigin(module: Module): SortedSet<Dependency> {
        return dependencies.filter { it.origin.name == module.name }.toSortedSet()
    }

    override fun getDependencyByDestination(module: Module): SortedSet<Dependency> {
        return dependencies.filter { it.destination.name == module.name }.toSortedSet()
    }

    override fun getDependencyByConfiguration(configuration: Configuration): SortedSet<Dependency> {
        return dependencies.filter { it.configuration == configuration }.toSortedSet()
    }

    override fun getAllDependencies(): SortedSet<Dependency> {
        return dependencies.toSortedSet()
    }
}
