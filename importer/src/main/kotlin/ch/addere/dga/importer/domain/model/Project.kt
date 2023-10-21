package ch.addere.dga.importer.domain.model

import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Dependency
import ch.addere.dga.graph.domain.model.Module
import ch.addere.dga.graph.domain.service.DependencyRepository
import ch.addere.dga.graph.domain.service.ModuleRepository
import ch.addere.dga.importer.application.service.GradleConnectorService

class Project(
    gradleConnectorService: GradleConnectorService,
    moduleRepository: ModuleRepository,
    dependencyRepository: DependencyRepository
) {

    private val gradleData = gradleConnectorService.connectToGradle()

    init {
        val modules = gradleData.projectModules
            .map { Module(it.projectModule) }
            .toSortedSet()
        moduleRepository.addModule(modules)

        gradleData.projectModuleDependencies
            .map { entry ->
                val origin = Module(entry.key)
                entry.value.map {
                    val destination = Module(it.destination.projectModule)
                    val configuration = Configuration(it.configuration)
                    val dependency = Dependency(origin, destination, configuration)

                    dependencyRepository.addDependency(dependency)
                }
            }
    }

    fun projectName(): String {
        return gradleData.projectName
    }
}
