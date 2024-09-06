package ch.addere.dga.core.domain.service

import ch.addere.dga.core.domain.model.Module

interface ModuleService {

    /**
     * Returns the number of modules.
     */
    fun nofModules(): Int

    /**
     * Returns all modules that matches a partial module name.
     * A partial module name contains at least on '*' asterisk.
     */
    fun resolvePartialModuleName(module: Module): Set<Module>
}
