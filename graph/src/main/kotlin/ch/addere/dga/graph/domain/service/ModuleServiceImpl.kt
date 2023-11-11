package ch.addere.dga.graph.domain.service

import ch.addere.dga.graph.domain.model.Module

class ModuleServiceImpl(private val moduleRepository: ModuleRepository) : ModuleService {

    override fun nofModules(): Int {
        return moduleRepository.getAllModules().size
    }

    override fun resolvePartialModuleName(module: Module): Set<Module> {
        val normalisedModuleName = module.name.replace("\\*+".toRegex(), "*")
        return recLookup(moduleRepository.getAllModules(), normalisedModuleName)
    }

    private fun recLookup(alreadyFoundModules: Set<Module>, nameToLookup: String): Set<Module> {
        return when {
            alreadyFoundModules.isEmpty() -> emptySet()
            nameToLookup == "*" -> alreadyFoundModules
            '*' !in nameToLookup -> lookupName(nameToLookup)
            nameToLookup.first() == '*' -> lookupWildcardStart(nameToLookup)
            nameToLookup.last() == '*' -> lookupWildcardEnd(nameToLookup)
            '*' in nameToLookup -> lookupWildcardMiddle(nameToLookup)
            else -> emptySet()
        }
    }

    private fun lookupName(lookupName: String): Set<Module> {
        val moduleByName = moduleRepository.getModuleByName(lookupName)
        return if (moduleByName != null) setOf(moduleByName) else emptySet()
    }

    private fun lookupWildcardStart(nameToLookup: String): Set<Module> {
        val nameEndingWith = nameToLookup.drop(1)
        val substringBefore = nameEndingWith.substringBefore('*')
        val matches = moduleRepository.getAllModulesEndingWith(substringBefore)
        return recLookup(matches, nameEndingWith) + matches
    }

    private fun lookupWildcardEnd(nameToLookup: String): Set<Module> {
        val nameStartingWith = nameToLookup.dropLast(1)
        val substringAfterLast = nameStartingWith.substringAfterLast('*')
        val matches = moduleRepository.getAllModulesStartingWith(substringAfterLast)
        return recLookup(matches, nameStartingWith) + matches
    }

    private fun lookupWildcardMiddle(nameToLookup: String): Set<Module> {
        val substringBefore = nameToLookup.substringBefore('*')
        val substringAfter = nameToLookup.substringAfter('*')

        val matchFirstPart = moduleRepository.getAllModulesStartingWith(substringBefore)
        val matchSecondPart = moduleRepository.getAllModulesEndingWith(substringAfter)
        return matchFirstPart.intersect(matchSecondPart)
    }
}
