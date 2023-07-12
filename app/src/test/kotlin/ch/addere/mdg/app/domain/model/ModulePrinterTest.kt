package ch.addere.mdg.app.domain.model

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.isEmpty
import ch.addere.mdg.graph.application.module.ModuleServiceImpl
import ch.addere.mdg.graph.domain.model.Module
import ch.addere.mdg.graph.domain.service.ModuleRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

private val M1 = Module("m1")
private val M2 = Module("m2")
private val M3 = Module("m3")

class ModulePrinterTest {

    private var log = mutableListOf<String>()

    @AfterEach
    fun `reset Log`() {
        log = mutableListOf()
    }

    @Test
    fun `test empty module service does not print anything`() {
        val printer = ModulePrinter(emptyModuleRepo(), consolePrinter())

        printer.printToConsole()

        assertThat(log).isEmpty()
    }

    @Test
    fun `test non-empty module service does not print anything`() {
        val printer = ModulePrinter(moduleRepo(), consolePrinter())

        printer.printToConsole()

        assertThat(log).containsExactly("", "m1, m2, m3")
    }

    private fun emptyModuleRepo(): ModuleServiceImpl {
        val emptyRepo = ModuleRepository()
        return ModuleServiceImpl(emptyRepo)
    }

    private fun moduleRepo(): ModuleServiceImpl {
        val repo = ModuleRepository()
        repo.addModule(setOf(M1, M2, M3))
        return ModuleServiceImpl(repo)
    }

    private fun consolePrinter(): ConsolePrinter {
        val argumentMock: CommandArgument = mock()
        whenever(argumentMock.printer).thenReturn(::logPrinter)
        return ConsolePrinter(argumentMock)
    }

    private fun logPrinter(input: String) {
        log.add(input)
    }
}
