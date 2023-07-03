package ch.addere.mdg.domain.model.writer

interface Writer {

    fun writeln(string: String)

    fun writeln(indentation: Int = 4, string: String) {
        writeln(" ".repeat(indentation) + string)
    }
}
