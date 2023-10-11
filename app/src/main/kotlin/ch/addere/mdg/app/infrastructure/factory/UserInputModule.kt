package ch.addere.mdg.app.infrastructure.factory

import ch.addere.mdg.app.domain.model.CommandArgument
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.io.File

val userInputModule = module {
    singleOf(::gradleProjectPath)
}

private fun gradleProjectPath(argument: CommandArgument): File {
    return argument.gradleProjectPath
}
