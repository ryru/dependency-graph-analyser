package ch.addere.mdg.domain.model.exporter

import ch.addere.mdg.domain.model.writer.Writer

interface Exporter {

    fun print(writer: Writer)
}
