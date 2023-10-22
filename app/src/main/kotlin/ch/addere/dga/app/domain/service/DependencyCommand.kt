package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.model.CommandArgument
import ch.addere.dga.app.domain.model.ConsolePrinter
import ch.addere.dga.app.domain.model.DependencyPrinter
import ch.addere.dga.app.domain.model.MermaidPrinter
import ch.addere.dga.app.domain.model.ModulePrinter
import ch.addere.dga.app.domain.model.OverviewPrinter

class DependencyCommand(
    private val argument: CommandArgument,
    private val dependencies: DependencyPrinter,
    private val mermaid: MermaidPrinter,
    private val modules: ModulePrinter,
    private val overview: OverviewPrinter,
    private val printer: ConsolePrinter,
) {

    fun run() {
        printer.println()
        overview.printToConsole()

        if (argument.optionsOutput.isAllModules) {
            modules.printToConsole()
        } else if (argument.optionsOutput.isAllConfigurations) {
            dependencies.printToConsole()
        } else if (argument.optionsOutput.isChartMermaid) {
            mermaid.printToConsole()
        }
        printer.println()
    }
}
