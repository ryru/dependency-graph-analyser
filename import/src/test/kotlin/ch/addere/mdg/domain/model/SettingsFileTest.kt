package ch.addere.mdg.domain.model

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import ch.addere.mdg.domain.model.GradleDsl.GROOVY
import ch.addere.mdg.domain.model.GradleDsl.KOTLIN
import org.junit.jupiter.api.Test
import java.io.File

private val APP = Module("app")
private val EXPORT = Module("export")
private val GRAPH = Module("graph")
private val IMPORT = Module("import")
private val LIST = Module("list")
private val UTILITIES = Module("utilities")

class SettingsFileTest {

    @Test
    fun `test kotlin settings file 1`() {
        val settings = SettingsFile(getFile("/settings-files/some.settings.gradle"))

        val modules = settings.getModules()

        assertThat(settings.gradleDsl()).isEqualTo(GROOVY)
        assertThat(modules).containsExactlyInAnyOrder(APP, EXPORT, GRAPH, IMPORT, LIST, UTILITIES)
    }

    @Test
    fun `test kotlin settings file 2`() {
        val settings = SettingsFile(getFile("/settings-files/someother.settings.gradle.kts"))

        val modules = settings.getModules()

        assertThat(settings.gradleDsl()).isEqualTo(KOTLIN)
        assertThat(modules).containsExactlyInAnyOrder(APP, EXPORT, GRAPH, IMPORT, LIST, UTILITIES)
    }

    @Test
    fun `test empty file`() {
        val settings = SettingsFile(getFile("/settings-files/empty.txt"))

        val modules = settings.getModules()

        assertThat(modules).isEmpty()
    }

    @Test
    fun `test random file`() {
        val settings = SettingsFile(getFile("/settings-files/randomfile.txt"))

        val modules = settings.getModules()

        assertThat(modules).isEmpty()
    }

    private fun getFile(path: String): File {
        return File(javaClass.getResource(path)!!.file)
    }
}
