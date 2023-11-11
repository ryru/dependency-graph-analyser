package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Module

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

    fun getAllModulesEndingWith(nameEndingWith: String): Set<Module> {
        return modules.filter { it.name.endsWith(nameEndingWith) }.toSet()
    }

    fun getAllModulesStartingWith(nameStartingWith: String): Set<Module> {
        return modules.filter { it.name.startsWith(nameStartingWith) }.toSet()
    }

    fun getAllModules(): Set<Module> {
        return modules
    }
}
