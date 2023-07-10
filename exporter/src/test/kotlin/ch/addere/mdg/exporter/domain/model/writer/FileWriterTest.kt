package ch.addere.mdg.exporter.domain.model.writer

import assertk.assertThat
import assertk.assertions.containsExactly
import org.junit.jupiter.api.Test
import java.io.File


private const val LINE_1 = "When Mr Bilbo Baggins of Bag End announced that he would "
private const val LINE_2 = "shortly be celebrating his eleventy-first birthday with a "
private const val LINE_3 = "party of special magnificence, there was much talk and "
private const val LINE_4 = "excitement in Hobbiton."

class FileWriterTest {

    private val file = File.createTempFile("test", "txt")

    @Test
    fun `test write into temp file`() {
        file.deleteOnExit()
        val fileWriter = FileWriter(file)

        fileWriter.writeln(LINE_1)
        fileWriter.writeln(LINE_2)
        fileWriter.writeln(LINE_3)
        fileWriter.writeln(LINE_4)

        assertThat(file.readLines()).containsExactly(LINE_1, LINE_2, LINE_3, LINE_4)
    }
}
