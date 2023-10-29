package ch.addere.dga.app.domain.service

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.Module

fun extractModules(dependencies: Set<Dependency>): Set<Module> {
    return dependencies.map { listOf(it.origin, it.destination) }.flatten().toSet()
}

fun configurationAndCount(dependencies: Set<Dependency>): Map<Configuration, Int> {
    return dependencies.associate {
        it.configuration to countConfigurations(
            dependencies,
            it.configuration
        )
    }
}

private fun countConfigurations(dependencies: Set<Dependency>, configuration: Configuration): Int {
    return dependencies.filter { it.configuration == configuration }.toSortedSet().count()
}

