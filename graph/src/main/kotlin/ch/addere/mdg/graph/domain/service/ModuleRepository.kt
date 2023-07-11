package ch.addere.mdg.graph.domain.service

import ch.addere.mdg.graph.domain.model.Module
import java.util.*

class ModuleRepository {

    private val modules = mutableSetOf<Module>()

    fun addModule(module: Module) {
        modules.add(module)
    }

    fun addModule(modules: Collection<Module>) {
        this.modules.addAll(modules)
    }

    fun getModuleByName(moduleName: String): Optional<Module> {
        return Optional.ofNullable(modules.first { it.name == moduleName })
    }

    fun getAllModules(): Set<Module> {
        return modules
    }
}
