package ch.addere.mdg.app.domain.model

class ConsolePrinter(argument: CommandArgument) {

    val printer = argument.printer

    fun println() {
        printer("")
    }

    fun println(input: String) {
        printer(input)
    }
}
