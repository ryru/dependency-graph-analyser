package ch.addere.mdg.exporter.domain.model.writer

import java.io.File
import java.io.FileOutputStream

class FileWriter(private val file: File) : Writer {

    override fun writeln(string: String) {
        FileOutputStream(file, true).bufferedWriter().use { it.appendLine(string) }
    }
}
