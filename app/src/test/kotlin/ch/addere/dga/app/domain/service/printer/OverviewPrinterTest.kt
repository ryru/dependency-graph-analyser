package ch.addere.dga.app.domain.service.printer

import assertk.assertThat
import assertk.assertions.containsExactly
import ch.addere.dga.app.domain.model.CommandArgument
import ch.addere.dga.app.domain.model.OverviewData
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class OverviewPrinterTest {

    private var log = mutableListOf<String>()

    @AfterEach
    fun `reset Log`() {
        log = mutableListOf()
    }

    @Test
    fun `test prints project`() {
        val printer = OverviewPrinter(consolePrinter())

        printer.printToConsole(OverviewData("test Project", 1, 2, 3))

        assertThat(log).containsExactly(
            "Analyse project \"test Project\"",
            "     1 modules",
            "     2 dependencies (3 unique)"
        )
    }

    private fun consolePrinter(): ConsolePrinter {
        val argumentMock: CommandArgument = Mockito.mock()
        whenever(argumentMock.printer).thenReturn(::logPrinter)
        return ConsolePrinter(argumentMock)
    }

    private fun logPrinter(input: String) {
        log.add(input)
    }
}
