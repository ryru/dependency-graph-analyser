package ch.addere.dga.app.domain.service.printer

import ch.addere.dga.app.domain.model.OverviewData

class OverviewPrinter(private val printer: ConsolePrinter) {

    fun printToConsole(overviewData: OverviewData) {

        printer.println("Analyse project \"${overviewData.projectName}\"")
        printer.println(String.format("%6d modules", overviewData.nofModules))
        printer.println(
            String.format(
                "%6d dependencies (%d unique configurations)",
                overviewData.nofDependencies,
                overviewData.nofUniqueDependencies
            )
        )
    }
}
