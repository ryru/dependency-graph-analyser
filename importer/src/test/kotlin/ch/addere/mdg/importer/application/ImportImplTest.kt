package ch.addere.mdg.importer.application

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.extracting
import assertk.assertions.hasSize
import ch.addere.mdg.domain.model.Module
import ch.addere.mdg.domain.model.graph.ModuleVertex
import ch.addere.mdg.importer.domain.model.Project
import org.junit.jupiter.api.Test
import java.io.File

private val APP = Module("app")
private val LIST = Module("list")
private val UTILITIES = Module("utilities")

class ImportImplTest {

    @Test
    fun `test import kotlin test project`() {
        val importer = ImportImpl()

        val dag = importer.readProject(Project(getFile("/projects/kotlin/settings.gradle.kts")))

        assertThat(dag.modules()).hasSize(3)
        assertThat(dag.modules())
            .extracting(ModuleVertex::module)
            .containsExactlyInAnyOrder(APP, LIST, UTILITIES)
    }

    @Test
    fun `test import groovy test project`() {
        val importer = ImportImpl()

        val dag = importer.readProject(Project(getFile("/projects/groovy/settings.gradle")))

        assertThat(dag.modules()).hasSize(3)
        assertThat(dag.modules())
            .extracting(ModuleVertex::module)
            .containsExactlyInAnyOrder(APP, LIST, UTILITIES)
    }

    private fun getFile(path: String): File {
        return File(javaClass.getResource(path)!!.file)
    }
}
