package ch.addere.mdg.application

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.extracting
import assertk.assertions.hasSize
import ch.addere.mdg.domain.model.GroovyBuildFile
import ch.addere.mdg.domain.model.KotlinBuildFile
import ch.addere.mdg.domain.model.Module
import ch.addere.mdg.domain.model.SettingsFile
import ch.addere.mdg.domain.model.graph.ModuleVertex
import org.junit.jupiter.api.Test
import java.io.File

private val APP = Module("app")
private val LIST = Module("list")
private val UTILITIES = Module("utilities")

class ImportImplTest {

    @Test
    fun `test import kotlin test project`() {
        val settingsFile = SettingsFile(getFile("/projects/kotlin/settings.gradle.kts"))
        val buildFiles = setOf(
            KotlinBuildFile(APP, getFile("/projects/kotlin/app/build.gradle.kts")),
            KotlinBuildFile(LIST, getFile("/projects/kotlin/list/build.gradle.kts")),
            KotlinBuildFile(UTILITIES, getFile("/projects/kotlin/utilities/build.gradle.kts"))
        )
        val importer = ImportImpl()

        val dag = importer.readProject(settingsFile, buildFiles)

        assertThat(dag.modules()).hasSize(3)
        assertThat(dag.modules())
            .extracting(ModuleVertex::module)
            .containsExactlyInAnyOrder(APP, LIST, UTILITIES)
    }

    @Test
    fun `test import groovy test project`() {
        val settingsFile = SettingsFile(getFile("/projects/groovy/settings.gradle"))
        val buildFiles = setOf(
            GroovyBuildFile(APP, getFile("/projects/groovy/app/build.gradle")),
            GroovyBuildFile(LIST, getFile("/projects/groovy/list/build.gradle")),
            GroovyBuildFile(UTILITIES, getFile("/projects/groovy/utilities/build.gradle"))
        )
        val importer = ImportImpl()

        val dag = importer.readProject(settingsFile, buildFiles)

        assertThat(dag.modules()).hasSize(3)
        assertThat(dag.modules())
            .extracting(ModuleVertex::module)
            .containsExactlyInAnyOrder(APP, LIST, UTILITIES)
    }

    private fun getFile(path: String): File {
        return File(javaClass.getResource(path)!!.file)
    }
}
