package ch.addere.dga.importer.domain.service

import ch.addere.dga.core.domain.DependencyRepository
import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Dependency
import ch.addere.dga.core.domain.model.Module
import ch.addere.dga.core.domain.service.ModuleRepository
import ch.addere.dga.dependencymodel.DependencyModel

class ModelImporterService(
    private val moduleRepository: ModuleRepository,
    private val dependencyRepository: DependencyRepository
) {
    fun import(dependencyModel: DependencyModel): String {
        val modules: Set<Module> =
            dependencyModel.projectModules.map { Module(it.projectModule) }.toSet()
        moduleRepository.addModule(modules)

        dependencyModel.projectModuleDependencies
            .forEach { (moduleName, configurationModelList) ->
                val origin = Module(moduleName)
                configurationModelList.map {
                    val destination = Module(it.destination.projectModule)
                    val configuration = Configuration(it.configuration)
                    dependencyRepository.addDependency(
                        Dependency(
                            origin,
                            destination,
                            configuration
                        )
                    )
                }
            }

        return dependencyModel.projectName
    }
}
