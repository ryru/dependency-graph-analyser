package ch.addere.dga.app

import ch.addere.dga.app.domain.model.CommandConfig
import ch.addere.dga.app.domain.model.FilterConfig
import ch.addere.dga.app.domain.model.OutputConfig
import ch.addere.dga.app.domain.service.DependencyCommand
import ch.addere.dga.app.infrastructure.factory.dgaModule
import ch.addere.dga.app.infrastructure.factory.userInputModule
import ch.addere.dga.graph.domain.model.Module
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.split
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

    private val optionsFilter by OptionsFilter()
    private val optionsOutput by OptionsOutput()

    override fun run() {
        val filterConfig = FilterConfig(optionsFilter.modules)
        val outputConfig = OutputConfig(
            optionsOutput.isAllModules,
            optionsOutput.isAllConfigurations,
            optionsOutput.isChartMermaid
        )
        val argument = CommandConfig(::echo, gradleProject, filterConfig, outputConfig)
        val command: DependencyCommand = get { parametersOf(argument) }

        command.run()
    }
}

class OptionsFilter : OptionGroup(
    name = "Filter Options",
    help = "Options controlling what to analyse."
) {
    val modules: List<Module>? by option("-m").convert("module1, module2") { Module(it) }.split(",")
        .help("Module names either in origin or destination. Specify multiple comma-separated module names.")
}

class OptionsOutput : OptionGroup(
    name = "Display Options",
    help = "Options controlling the output of the analysis."
) {
    val isAllModules: Boolean by option("--modules")
        .flag()
        .help("Shows all modules ordered alphabetically")

    val isAllConfigurations: Boolean by option("--configurations")
        .flag()
        .help("Shows all configurations ordered by occurrence")

    val isChartMermaid: Boolean by option("--chart-mermaid")
        .flag()
        .help("Generate text chart that can be visualised by Mermaid")
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
