package ch.addere.mdg.graph.application.module

import ch.addere.mdg.graph.domain.service.ModuleRepository

class ModuleServiceImpl(private val moduleRepository: ModuleRepository) : ModuleService {

    override fun nofModules(): Int {
        return moduleRepository.getAllModules().size
    }
}
