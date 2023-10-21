package ch.addere.dga.exporter.domain.model.writer

class ConsoleWriter(private val writer: (String) -> Unit) : Writer {

    override fun writeln(string: String) {
        writer(string)
    }
}
