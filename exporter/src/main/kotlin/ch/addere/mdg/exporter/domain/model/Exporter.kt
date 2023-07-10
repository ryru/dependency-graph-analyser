package ch.addere.mdg.exporter.domain.model

import ch.addere.mdg.exporter.domain.model.writer.Writer

interface Exporter {

    fun print(writer: Writer)
}
