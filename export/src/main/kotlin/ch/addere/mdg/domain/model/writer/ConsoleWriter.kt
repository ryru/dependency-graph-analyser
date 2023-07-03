package ch.addere.mdg.domain.model.writer

class ConsoleWriter : Writer {

    override fun writeln(string: String) {
        println(string)
    }
}
