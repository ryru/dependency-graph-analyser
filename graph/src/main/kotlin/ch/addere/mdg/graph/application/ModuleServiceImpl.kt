package ch.addere.mdg.graph.application

import ch.addere.mdg.graph.domain.model.Module
import ch.addere.mdg.graph.domain.service.ModuleRepository
import java.util.*

class ModuleServiceImpl(private val moduleRepository: ModuleRepository) : ModuleService {

    override fun nofModules(): Int {
        return moduleRepository.getAllModules().size
    }

    override fun modules(): SortedSet<Module> {
        return moduleRepository.getAllModules().toSortedSet()
    }
}
