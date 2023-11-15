package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Module

class ModuleServiceImpl(private val moduleRepository: ModuleRepository) : ModuleService {

    override fun nofModules(): Int {
        return moduleRepository.getAllModules().size
    }

    override fun resolvePartialModuleName(module: Module): Set<Module> {
        val normalisedModuleName = module.name.replace("\\*+".toRegex(), ".*")
        return recLookup(moduleRepository.getAllModules(), normalisedModuleName)
    }

    private fun recLookup(alreadyFoundModules: Set<Module>, nameToLookup: String): Set<Module> {
        return when {
            alreadyFoundModules.isEmpty() -> emptySet()
            '*' !in nameToLookup -> lookupName(nameToLookup)
            else -> regexLookup(nameToLookup.toRegex())
        }
    }

    private fun lookupName(lookupName: String): Set<Module> {
        val moduleByName = moduleRepository.getModuleByName(lookupName)
        return if (moduleByName != null) setOf(moduleByName) else emptySet()
    }

    private fun regexLookup(regexNameToLookup: Regex): Set<Module> {
        return moduleRepository.getAllModules()
            .filter { it.name.matches(regexNameToLookup) }
            .toSet()
    }
}
