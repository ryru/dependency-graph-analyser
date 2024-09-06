package ch.addere.dga.app.domain.model

import ch.addere.dga.app.domain.model.writer.Writer


interface Exporter {

    fun print(writer: Writer)
}
