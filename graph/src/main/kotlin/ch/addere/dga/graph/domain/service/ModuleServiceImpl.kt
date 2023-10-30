package ch.addere.dga.graph.domain.service

class ModuleServiceImpl(private val moduleRepository: ModuleRepository) : ModuleService {

    override fun nofModules(): Int {
        return moduleRepository.getAllModules().size
    }
}
