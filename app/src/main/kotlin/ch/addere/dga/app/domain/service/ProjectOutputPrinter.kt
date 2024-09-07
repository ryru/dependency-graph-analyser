package ch.addere.dga.app.domain.service

import ch.addere.dga.app.configuration.OutputOptions
import ch.addere.dga.app.configuration.OutputOptions.OutputOptionConfigurations
import ch.addere.dga.app.configuration.OutputOptions.OutputOptionMermaid
import ch.addere.dga.app.configuration.OutputOptions.OutputOptionModules
import ch.addere.dga.app.configuration.OutputOptions.OutputOptionOverviewOnly
import ch.addere.dga.app.domain.service.printer.DependencyPrinter
import ch.addere.dga.app.domain.service.printer.MermaidPrinter
import ch.addere.dga.app.domain.service.printer.ModulePrinter
import ch.addere.dga.core.domain.model.Dependency

class ProjectOutputPrinter(
    private val dependencies: DependencyPrinter,
    private val mermaid: MermaidPrinter,
    private val modulePrinter: ModulePrinter,
) {

    /**
     * Print analysed and filtered Gradle project according to the specified output option.
     */
    fun output(outputConfig: OutputOptions, relevantDependencies: Set<Dependency>) {
        when (outputConfig) {
            OutputOptionOverviewOnly -> return
            OutputOptionModules -> modulePrinter.printToConsole(extractModules(relevantDependencies))
            OutputOptionConfigurations -> dependencies.printToConsole(
                configurationAndCount(
                    relevantDependencies
                )
            )

            OutputOptionMermaid -> mermaid.printToConsole(relevantDependencies)
        }
    }
}
