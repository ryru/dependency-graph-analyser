package ch.addere.dga.app.domain.service.printer

import ch.addere.dga.app.domain.model.CommandConfig

class ConsolePrinter(config: CommandConfig) {

    val printer = config.printer

    fun println() {
        printer("")
    }

    fun println(input: String) {
        printer(input)
    }
}
