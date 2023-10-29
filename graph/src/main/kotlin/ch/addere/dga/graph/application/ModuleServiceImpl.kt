package ch.addere.dga.graph.application

import ch.addere.dga.graph.domain.service.ModuleRepository

class ModuleServiceImpl(private val moduleRepository: ModuleRepository) : ModuleService {

    override fun nofModules(): Int {
        return moduleRepository.getAllModules().size
    }
}
