package ch.addere.dga.app.domain.model

import java.io.File

data class CommandArgument(
    val printer: (String) -> Unit,
    val gradleProjectPath: File,
    val isMermaidGraph: Boolean,
    val isAllModules: Boolean,
    val isAllConfigurations: Boolean
)
