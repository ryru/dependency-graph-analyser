package ch.addere.dga.app.domain.service.printer

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import ch.addere.dga.app.domain.model.CommandConfig
import ch.addere.dga.graph.domain.model.Module
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ModulePrinterTest {

    private val m1 = Module("m1")
    private val m2 = Module("m2")
    private val m3 = Module("m3")
    private var log = mutableListOf<String>()

    @AfterEach
    fun `reset Log`() {
        log = mutableListOf()
    }

    @Test
    fun `test empty module set does not print anything`() {
        val printer = ModulePrinter(consolePrinter())

        printer.printToConsole(emptySet())

        assertThat(log).isEmpty()
    }

    @Test
    fun `test non-empty module set prints all modules`() {
        val printer = ModulePrinter(consolePrinter())

        printer.printToConsole(setOf(m1, m2, m3))

        assertThat(log).containsExactly("", "m1, m2, m3")
    }

    private fun consolePrinter(): ConsolePrinter {
        val configMock: CommandConfig = mock()
        whenever(configMock.printer).thenReturn(::logPrinter)
        return ConsolePrinter(configMock)
    }

    private fun logPrinter(input: String) {
        log.add(input)
    }
}
