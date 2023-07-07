package ch.addere.mdg.domain.model.exporter.mermaid

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import ch.addere.mdg.domain.model.minimalDirect
import ch.addere.mdg.domain.model.minimalIndirect
import ch.addere.mdg.domain.model.writer.TestWriter
import org.junit.jupiter.api.Test
import java.util.*

class MermaidPartialGraphExporterTest {

    @Test
    fun `test a minimal DAG`() {
        val exporter = MermaidPartialGraphExporter(minimalDirect(), minimalIndirect())
        val testWriter = TestWriter()
        exporter.print(testWriter)

        assertThat(testWriter.toStringBlock()).isEqualTo(
            """
                graph TD
                    vae7be2(m1) -->|c1| vaaf2f8(m2)
                    vae7be2(m1) -->|c1| vfd6b6f(m4)
                    vaaf2f8(m2) -.->|c2| v9678f7(m3)
                    vfd6b6f(m4) -.->|c1| vaaf2f8(m2)"""
                .trimIndent()
        )
    }

    @Test
    fun `test an empty DAG`() {
        val exporter = MermaidFullGraphExporter(Collections.emptySortedSet())
        val testWriter = TestWriter()
        exporter.print(testWriter)
        assertThat(testWriter.toStringBlock()).isEmpty()
    }
}
