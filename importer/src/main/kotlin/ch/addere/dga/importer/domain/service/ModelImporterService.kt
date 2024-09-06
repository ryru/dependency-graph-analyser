package ch.addere.dga.importer.domain.service

import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module
import ch.addere.dga.core.domain.service.DependencyRepository
import ch.addere.dga.core.domain.service.ModuleRepository
import ch.addere.dga.dependencymodel.DependencyModel

class ModelImporterService(
    private val moduleRepository: ModuleRepository,
    private val dependencyRepository: DependencyRepository
) {
    fun import(dependencyModel: DependencyModel): String {
        val modules = dependencyModel.projectModules
            .map { Module(it.projectModule) }
            .toSortedSet()
        moduleRepository.addModule(modules)

        dependencyModel.projectModuleDependencies
            .map { entry ->
                val origin = Module(entry.key)
                entry.value.map {
                    val destination = Module(it.destination.projectModule)
                    val configuration = Configuration(it.configuration)
                    val dependency = Dependency(origin, destination, configuration)

                    dependencyRepository.addDependency(dependency)
                }
            }

        return dependencyModel.projectName
    }
}
