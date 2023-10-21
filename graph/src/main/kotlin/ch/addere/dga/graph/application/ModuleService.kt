package ch.addere.dga.graph.application

import ch.addere.dga.graph.domain.model.Module
import java.util.*

interface ModuleService {

    /**
     * Returns the number of modules.
     */
    fun nofModules(): Int

    /**
     * Returns all modules in alphabetic order.
     */
    fun modules(): SortedSet<Module>
}
