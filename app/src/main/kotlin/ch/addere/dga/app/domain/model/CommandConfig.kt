package ch.addere.dga.app.domain.model

import ch.addere.dga.app.configuration.OutputOptions
import java.io.File

data class CommandConfig(
    val printer: (String) -> Unit,
    val gradleProjectPath: File,
    val filterConfig: FilterConfig,
    val outputConfig: OutputOptions
)
