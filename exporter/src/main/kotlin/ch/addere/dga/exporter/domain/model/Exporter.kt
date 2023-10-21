package ch.addere.dga.exporter.domain.model

import ch.addere.dga.exporter.domain.model.writer.Writer

interface Exporter {

    fun print(writer: Writer)
}
