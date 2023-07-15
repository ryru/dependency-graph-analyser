package ch.addere.mdg.app.domain.model

import ch.addere.mdg.graph.application.ModuleService
import ch.addere.mdg.graph.domain.model.Module

class ModulePrinter(
    private val moduleService: ModuleService,
    private val printer: ConsolePrinter,
) {

    fun printToConsole() {
        if (moduleService.modules().isNotEmpty()) {
            printer.println()
            printer.println(moduleService.modules().map(Module::name).joinToString(", "))
        }
    }
}
