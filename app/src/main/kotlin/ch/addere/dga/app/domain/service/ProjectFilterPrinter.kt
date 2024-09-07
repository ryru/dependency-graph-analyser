package ch.addere.dga.app.domain.service

import ch.addere.dga.app.domain.service.printer.ConsolePrinter
import ch.addere.dga.core.domain.model.Dependency

class ProjectFilterPrinter(private val printer: ConsolePrinter) {

    /**
     * Print filtered module and configuration information.
     */
    fun output(dependencies: Set<Dependency>) {
        val overviewData = filteredData(dependencies)
        printer.println()
        printer.println("Applying filter on data results in:")
        printer.println(String.format("%6d modules", overviewData.nofModules))
        printer.println(
            String.format(
                "%6d dependency configurations (%d unique dependency configurations)",
                overviewData.nofDependencies,
                overviewData.nofUniqueDependencies
            )
        )
    }

    private fun filteredData(dependencies: Set<Dependency>): FilterData {
        val nofModules = dependencies.flatMap { setOf(it.origin, it.destination) }.toSet().size
        val nofDependencies = dependencies.map { it.configuration }.count()
        val nofUniqueDependencies = dependencies.map { it.configuration }.toSet().size
        return FilterData(nofModules, nofDependencies, nofUniqueDependencies)
    }

    private data class FilterData(
        val nofModules: Int,
        val nofDependencies: Int,
        val nofUniqueDependencies: Int
    )
}
