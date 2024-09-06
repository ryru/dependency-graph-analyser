package ch.addere.dga.core.domain.service

import ch.addere.dga.core.domain.model.Module

class ModuleRepository {

    private val modules = mutableSetOf<Module>()

    fun addModule(module: Module) {
        modules.add(module)
    }

    fun addModule(modules: Collection<Module>) {
        this.modules.addAll(modules)
    }

    fun getModuleByName(moduleName: String): Module? {
        return try {
            modules.first { it.name == moduleName }
        } catch (e: NoSuchElementException) {
            null
        }
    }

    fun getAllModules(): Set<Module> {
        return modules
    }
}
