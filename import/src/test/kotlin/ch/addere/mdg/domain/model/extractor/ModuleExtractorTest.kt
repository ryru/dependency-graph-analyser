package ch.addere.mdg.domain.model.extractor

import assertk.assertThat
import assertk.assertions.containsExactlyInAnyOrder
import ch.addere.mdg.domain.model.Module
import org.junit.jupiter.api.Test

private val M1 = Module("m1")
private val M2 = Module("m2")
private val M3 = Module("m3")
private val M4 = Module("m4")
private val M5 = Module("m5")
private val M6 = Module("m6")
private val M7 = Module("m7")

class ModuleExtractorTest {

    @Test
    fun `test kotlin include on one line`() {
        val includeString = "include(\"m1\", \"m2\", \"m3\", \"m4\", \"m5\", \"m6\", \"m7\")"

        val result = findModulesInKotlinSettings(includeString)

        assertThat(result).containsExactlyInAnyOrder(M1, M2, M3, M4, M5, M6, M7)
    }

    @Test
    fun `test kotlin include on multi line`() {
        val includeString = """
            include(
            "m1",
            "m2",
            "m3",
            "m4",
            "m5",
            "m6",
            "m7",
            )
        """.trimIndent()

        val result = findModulesInKotlinSettings(includeString)

        assertThat(result).containsExactlyInAnyOrder(M1, M2, M3, M4, M5, M6, M7)
    }

    @Test
    fun `test groovy include on one line`() {
        val includeString = "include 'm1', 'm2', 'm3', 'm4', 'm5', 'm6', 'm7'"

        val result = findModulesInGroovySettings(includeString)

        assertThat(result).containsExactlyInAnyOrder(M1, M2, M3, M4, M5, M6, M7)
    }

    @Test
    fun `test groovy include on multi line`() {
        val includeString = """
            include
            'm1',
            'm2',
            'm3',
            'm4',
            'm5',
            'm6',
            'm7'
        """.trimMargin()

        val result = findModulesInGroovySettings(includeString)

        assertThat(result).containsExactlyInAnyOrder(M1, M2, M3, M4, M5, M6, M7)
    }
}
