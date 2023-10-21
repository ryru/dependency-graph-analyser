package ch.addere.dga.app.domain.model

class ConsolePrinter(argument: CommandArgument) {

    val printer = argument.printer

    fun println() {
        printer("")
    }

    fun println(input: String) {
        printer(input)
    }
}
