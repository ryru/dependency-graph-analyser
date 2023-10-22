package ch.addere.dga.app.domain.model

import ch.addere.dga.app.OptionsOutput
import java.io.File

data class CommandArgument(
    val printer: (String) -> Unit,
    val gradleProjectPath: File,
    val optionsOutput: OptionsOutput
)
