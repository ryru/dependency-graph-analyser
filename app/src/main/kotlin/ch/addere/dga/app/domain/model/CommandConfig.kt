package ch.addere.dga.app.domain.model

import java.io.File

data class CommandConfig(
    val printer: (String) -> Unit,
    val gradleProjectPath: File,
    val filterConfig: FilterConfig,
    val outputConfig: OutputConfig
)
