package ch.addere.dga.app.infrastructure.factory

import ch.addere.dga.app.domain.model.CommandConfig
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.io.File

val userInputModule = module {
    singleOf(::gradleProjectPath)
}

private fun gradleProjectPath(config: CommandConfig): File {
    return config.gradleProjectPath
}
