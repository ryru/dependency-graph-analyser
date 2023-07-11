package ch.addere.mdg.app.domain.model

import java.io.File

data class CommandArgument(
    val printer: (String) -> Unit,
    val settings: File,
    val isMermaidGraph: Boolean
)
