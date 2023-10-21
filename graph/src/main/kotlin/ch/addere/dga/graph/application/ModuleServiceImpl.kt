package ch.addere.dga.graph.application

import ch.addere.dga.graph.domain.service.ModuleRepository
import ch.addere.dga.graph.domain.model.Module
import java.util.*

class ModuleServiceImpl(private val moduleRepository: ModuleRepository) : ModuleService {

    override fun nofModules(): Int {
        return moduleRepository.getAllModules().size
    }

    override fun modules(): SortedSet<Module> {
        return moduleRepository.getAllModules().toSortedSet()
    }
}
