package ch.addere.dga.app

import ch.addere.dga.app.domain.model.CommandArgument
import ch.addere.dga.app.domain.service.DependencyCommand
import ch.addere.dga.app.infrastructure.factory.dgaModule
import ch.addere.dga.app.infrastructure.factory.userInputModule
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.parameter.parametersOf
import java.io.File
import kotlin.system.exitProcess

class Dga : CliktCommand(help = "Analyse the module dependency graph of a Gradle project."),
    KoinComponent {

    private val gradleProject: File by argument()
        .file()
        .help("Path of the Gradle project directory")

    private val isChartMermaid: Boolean by option("--chart-mermaid")
        .flag()
        .help("Generate text chart that can be visualised by Mermaid")

    private val isAllModules: Boolean by option("--modules")
        .flag()
        .help("Shows all modules ordered alphabetically")

    private val isAllConfigurations: Boolean by option("--configurations")
        .flag()
        .help("Shows all configurations ordered by occurrence")

    override fun run() {
        val argument =
            CommandArgument(
                ::echo,
                gradleProject,
                isChartMermaid,
                isAllModules,
                isAllConfigurations
            )
        val command: DependencyCommand = get { parametersOf(argument) }

        command.run()
    }
}

fun main(args: Array<String>) {
    val dga = Dga()
    try {
        startKoin {
            modules(userInputModule)
            modules(dgaModule)
        }

        dga.parse(args)
    } catch (e: CliktError) {
        dga.echoFormattedHelp(e)
        exitProcess(e.statusCode)
    } catch (e: Exception) {
        dga.echo(e.message, err = true)
        exitProcess(1)
    }
}
