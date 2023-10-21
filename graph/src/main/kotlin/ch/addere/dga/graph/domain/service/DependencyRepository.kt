package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.Module
import java.util.*

class DependencyRepository {

    private val dependencies = mutableSetOf<Dependency>()

    fun addDependency(dependency: Dependency) {
        dependencies.add(dependency)
    }

    fun addDependency(dependencies: Collection<Dependency>) {
        this.dependencies.addAll(dependencies)
    }

    fun getDependencyByOrigin(module: Module): SortedSet<Dependency> {
        return dependencies.filter { it.origin.name == module.name }.toSortedSet()
    }

    fun getDependencyByDestination(module: Module): SortedSet<Dependency> {
        return dependencies.filter { it.destination.name == module.name }.toSortedSet()
    }

    fun getDependencyByConfiguration(configuration: Configuration): SortedSet<Dependency> {
        return dependencies.filter { it.configuration == configuration }.toSortedSet()
    }

    fun getAllDependencies(): SortedSet<Dependency> {
        return dependencies.toSortedSet()
    }
}
