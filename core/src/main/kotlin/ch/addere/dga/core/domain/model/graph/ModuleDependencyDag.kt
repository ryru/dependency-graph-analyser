package ch.addere.dga.core.domain.model.graph

import ch.addere.dga.core.domain.DependencyRepository
import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module
import ch.addere.dga.core.domain.service.ModuleRepository
import java.util.*

class ModuleDependencyDag(
    private val moduleRepository: ModuleRepository,
    private val dependencyRepository: DependencyRepository
) {

    private fun toDependencyEdge(dependency: Dependency): DependencyEdge {
        val origin = resolve(dependency.origin)
        val destination = resolve(dependency.destination)
        val dependencyEdge = DependencyEdge(origin, destination, dependency.configuration)

        origin.addOutgoing(destination, dependencyEdge)
        destination.addIncoming(origin, dependencyEdge)

        return dependencyEdge
    }

    private fun resolve(module: Module): ModuleVertex {
        return moduleRepository.getAllModules().map(::ModuleVertex).toSet()
            .first { it.module == module }
    }

    fun nofVertices(): Int {
        return moduleRepository.getAllModules().map(::ModuleVertex).toSet().size
    }

    fun nofEdges(): Int {
        return dependencyRepository.getAllDependencies().map(::toDependencyEdge).toSet().size
    }

    fun vertices(): Set<ModuleVertex> {
        return moduleRepository.getAllModules().map(::ModuleVertex).toSet()
    }

    fun vertex(module: Module): Optional<ModuleVertex> {
        return Optional.ofNullable(
            moduleRepository.getAllModules().map(::ModuleVertex).toSet()
                .firstOrNull { it.module == module })
    }

    fun edges(): Set<DependencyEdge> {
        return dependencyRepository.getAllDependencies().map(::toDependencyEdge).toSet()
    }

    fun edge(configuration: Collection<Configuration>): Set<DependencyEdge> {
        return dependencyRepository.getAllDependencies().map(::toDependencyEdge).toSet()
            .filter { configuration.contains(it.configuration) }.toSet()
    }
}
