package ch.addere.dga.app.domain.model

import ch.addere.dga.app.configuration.OutputOptions
import ch.addere.dga.app.configuration.OutputOptions.OutputOptionOverviewOnly
import java.io.File

data class CommandConfig(
    val printer: (String) -> Unit,
    val gradleProjectPath: File,
    val filterConfig: FilterConfig,
    val outputConfig: OutputOptions
) {
    val hasActiveFilter: Boolean =
        filterConfig.modules.isNotEmpty() ||
            filterConfig.originModules.isNotEmpty() ||
            filterConfig.destinationModules.isNotEmpty() ||
            filterConfig.configurations.isNotEmpty() ||
            filterConfig.includeTransitiveDependencies
    val hasActiveOutput: Boolean = outputConfig != OutputOptionOverviewOnly
}
