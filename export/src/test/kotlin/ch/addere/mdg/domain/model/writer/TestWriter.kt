package ch.addere.mdg.domain.model.writer

class TestWriter : Writer {

    private val log = mutableListOf<String>()

    override fun writeln(string: String) {
        log.add(string)
    }

    fun toStringBlock(): String {
        return log.joinToString("\n")
    }
}
