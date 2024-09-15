package ch.addere.dga.app

import ch.addere.dga.app.configuration.OutputOptions
import ch.addere.dga.app.domain.model.CommandConfig
import ch.addere.dga.app.domain.model.FilterConfig
import ch.addere.dga.app.domain.service.DependencyCommandHandler
import ch.addere.dga.app.infrastructure.factory.coreModule
import ch.addere.dga.app.infrastructure.factory.dgaModule
import ch.addere.dga.app.infrastructure.factory.importerModule
import ch.addere.dga.app.infrastructure.factory.userInputModule
import ch.addere.dga.app.infrastructure.service.AppVersionService
import ch.addere.dga.core.domain.model.Configuration
import ch.addere.dga.core.domain.model.Module
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.groups.OptionGroup
import com.github.ajalt.clikt.parameters.groups.default
import com.github.ajalt.clikt.parameters.groups.mutuallyExclusiveOptions
import com.github.ajalt.clikt.parameters.groups.provideDelegate
import com.github.ajalt.clikt.parameters.groups.single
import com.github.ajalt.clikt.parameters.options.convert
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.deprecated
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.split
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.file
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.parameter.parametersOf
import java.io.File
import kotlin.system.exitProcess

private class Dga : CliktCommand(help = "Analyse the module dependency graph of a Gradle project."),
    KoinComponent {

    private val appVersionService = AppVersionService()

    init {
        val appVersion = appVersionService.readVersions()
        val versionOutput = """
            ${appVersion.cli}
            dga connector plugin version ${appVersion.plugin}
        """.trimIndent()
        versionOption(versionOutput, names = setOf("--version", "-v"))
    }

    private val gradleProject: File by argument()
        .file()
        .help("Path of the Gradle project directory")

    private val optionsFilter by OptionsFilter()
    private val outputOption: OutputOptions by mutuallyExclusiveOptions(
        option("--modules")
            .flag()
            .convert { OutputOptions.OutputOptionModules }
            .help("Shows all modules of the project applying to the specified filters."),
        option("--configurations")
            .flag()
            .convert { OutputOptions.OutputOptionConfigurations }
            .help("Displays all configurations applying to the specified filters and sorted by frequency of occurrence."),
        option("--mermaid-graph")
            .flag()
            .convert { OutputOptions.OutputOptionMermaid }
            .help("Generate the Mermaid graph chart source for the dependencies fulfilling the filter criteria."),
        option("--chart-mermaid")
            .flag()
            .convert { OutputOptions.OutputOptionMermaid }
            .help("Generate the Mermaid graph chart source for the dependencies fulfilling the filter criteria.")
            .deprecated("Use --mermaid-graph instead"),
        help = "Options controlling how to output the analysed data. Display options can not be combined.",
        name = "Display Options",
    ).single().default(OutputOptions.OutputOptionOverviewOnly)

    override fun run() {
        val filterConfig = FilterConfig(
            optionsFilter.modules,
            optionsFilter.originModules,
            optionsFilter.destinationModules,
            optionsFilter.configurations,
            optionsFilter.includeTransitiveModules
        )
        val argument = CommandConfig(::echo, gradleProject, filterConfig, outputOption)
        val command: DependencyCommandHandler = get { parametersOf(argument) }

        command.run()
    }
}

private class OptionsFilter : OptionGroup(
    name = "Filter Options",
    help = """
        Filter control what to analyse. If several filters are set, dependencies must fulfill all of them.
        Without any set filter the whole Gradle project will be processed.
        
        Use an '*' asterisk to specify multiple modules or configurations without writing each of them.
        
        E.g.:
          - "importer-*" resolves to all modules starting with 'importer-'.
          - "*-exporter" resolves to all modules ending with '-exporter'.
          - "file-*-exporter" resolves to all modules starting with 'file-' and ending with '-exporter'.
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

    val includeTransitiveModules: Boolean by option("-t", "--transitive")
        .flag()
        .help("If set, also include transitive module dependencies. This applies only if a module filter is active.")
}

fun main(args: Array<String>) {
    val dga = Dga()
    try {
        startKoin {
            modules(userInputModule)
            modules(dgaModule)
            modules(importerModule)
            modules(coreModule)
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
