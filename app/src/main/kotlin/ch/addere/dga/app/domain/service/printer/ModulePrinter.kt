package ch.addere.dga.app.domain.service.printer

import ch.addere.dga.graph.domain.model.Module

class ModulePrinter(private val printer: ConsolePrinter) {

    fun printToConsole(modules: Set<Module>) {
        if (modules.isNotEmpty()) {
            printer.println()
            printer.println(modules.map(Module::name).joinToString(", "))
        }
    }
}
