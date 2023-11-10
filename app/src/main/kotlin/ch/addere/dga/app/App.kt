package ch.addere.dga.app

import ch.addere.dga.app.domain.model.CommandConfig
import ch.addere.dga.app.domain.model.FilterConfig
import ch.addere.dga.app.domain.model.OutputConfig
import ch.addere.dga.app.domain.service.DependencyCommand
import ch.addere.dga.app.infrastructure.factory.dgaModule
import ch.addere.dga.app.infrastructure.factory.userInputModule
import ch.addere.dga.graph.domain.model.Configuration
import ch.addere.dga.graph.domain.model.Module
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
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
        val filterConfig = FilterConfig(
            optionsFilter.modules,
            optionsFilter.originModules,
            optionsFilter.destinationModules,
            optionsFilter.configurations
        )
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
    help = """
        Filter control what to analyse. If several filters are set, dependencies must fulfil all of them.
        Without any set filter the whole Gradle project will be processed.
        """.trimIndent()
) {
    val modules: List<Module> by option("-m")
        .convert("module,...") { Module(it) }.split(",").default(emptyList())
        .help("Module names either in origin or destination. Specify multiple comma-separated module names.")

    val originModules: List<Module> by option("-o")
        .convert("module,...") { Module(it) }.split(",").default(emptyList())
        .help("Module names in origin. Specify multiple comma-separated module names.")

    val destinationModules: List<Module> by option("-d")
        .convert("module,...") { Module(it) }.split(",").default(emptyList())
        .help("Module names in destination. Specify multiple comma-separated module names.")

    val configurations: List<Configuration> by option("-c")
        .convert("configuration,...") { Configuration(it) }.split(",").default(emptyList())
        .help("Configurations used in dependencies. Specify multiple comma-separated configuration names.")
}

class OptionsOutput : OptionGroup(
    name = "Display Options",
    help = """
        Options controlling how to output the analysed data. Display options can not be combined.
        """.trimIndent()
) {
    val isAllModules: Boolean by option("--modules")
        .flag()
        .help("Shows all modules of the project applying to the specified filters.")

    val isAllConfigurations: Boolean by option("--configurations")
        .flag()
        .help("Displays all configurations applying to the specified filters and sorted by frequency of occurrence.")

    val isChartMermaid: Boolean by option("--chart-mermaid")
        .flag()
        .help("Generate the Mermaid graph chart source for the dependencies fulfilling the filter criteria.")
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
