package ch.addere.dga.app.domain.service.printer

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import ch.addere.dga.app.domain.model.CommandConfig
import ch.addere.dga.graph.domain.model.Configuration
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.whenever

class DependencyPrinterTest {

    private val c1 = Configuration("c1")
    private val c2 = Configuration("c2")
    private val c3 = Configuration("c3")
    private var log = mutableListOf<String>()

    @AfterEach
    fun `reset Log`() {
        log = mutableListOf()
    }

    @Test
    fun `test empty configuration map does not print anything`() {
        val printer = DependencyPrinter(consolePrinter())

        printer.printToConsole(emptyMap())

        assertThat(log).isEmpty()
    }

    @Test
    fun `test non-empty configuration map prints all dependencies`() {
        val printer = DependencyPrinter(consolePrinter())

        printer.printToConsole(mapOf(c1 to 23, c2 to 42, c3 to 1337))

        assertThat(log).containsExactly("", "c3, 1337", "c2, 42", "c1, 23")
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
