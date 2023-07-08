package ch.addere.mdg.domain.model

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.containsExactlyInAnyOrder
import assertk.assertions.isEmpty
import org.junit.jupiter.api.Test
import java.io.File

private val M1 = Module("m1")
private val M2 = Module("m2")
private val M3 = Module("m3")
private val C1 = Configuration("c1")
private val C2 = Configuration("c2")
private val D1 = Dependency(M1, M2, C1)
private val D2 = Dependency(M1, M3, C2)

class GroovyBuildFileTest {

    @Test
    fun `test build with single project dependency on one line`() {
        val build = GroovyBuildFile(M1, getFile("/build-files/groovy/onelinder.build.gradle"))

        val dependencies = build.dependencies

        assertThat(dependencies).contains(D1)
    }

    @Test
    fun `test build with multiple project dependencies`() {
        val build =
            GroovyBuildFile(
                M1,
                getFile("/build-files/groovy/multidependencies.build.gradle")
            )

        val dependencies = build.dependencies

        assertThat(dependencies).containsExactlyInAnyOrder(D1, D2)
    }

    @Test
    fun `test build without project dependency`() {
        val build =
            GroovyBuildFile(M2, getFile("/build-files/groovy/noprojectdependency.build.gradle"))

        val dependencies = build.dependencies

        assertThat(dependencies).isEmpty()
    }

    @Test
    fun `test empty file`() {
        val build = GroovyBuildFile(M2, getFile("/build-files/empty.txt"))

        val dependencies = build.dependencies

        assertThat(dependencies).isEmpty()
    }

    @Test
    fun `test random file`() {
        val build = GroovyBuildFile(M1, getFile("/build-files/randomfile.txt"))

        val dependencies = build.dependencies

        assertThat(dependencies).isEmpty()
    }

    private fun getFile(path: String): File {
        return File(javaClass.getResource(path)!!.file)
    }
}
