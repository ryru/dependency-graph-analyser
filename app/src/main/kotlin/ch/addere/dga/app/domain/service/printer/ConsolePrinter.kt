package ch.addere.dga.app.domain.service.printer

import ch.addere.dga.app.domain.model.CommandArgument

class ConsolePrinter(argument: CommandArgument) {

    val printer = argument.printer

    fun println() {
        printer("")
    }

    fun println(input: String) {
        printer(input)
    }
}
