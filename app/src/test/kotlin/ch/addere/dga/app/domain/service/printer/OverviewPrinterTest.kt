package ch.addere.dga.app.domain.service.printer

import assertk.assertThat
import assertk.assertions.containsExactly
import ch.addere.dga.app.domain.model.CommandConfig
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
            "     2 dependencies (3 unique configurations)"
        )
    }

    private fun consolePrinter(): ConsolePrinter {
        val configMock: CommandConfig = Mockito.mock()
        whenever(configMock.printer).thenReturn(::logPrinter)
        return ConsolePrinter(configMock)
    }

    private fun logPrinter(input: String) {
        log.add(input)
    }
}
