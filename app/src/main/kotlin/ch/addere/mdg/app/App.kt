/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package ch.addere.mdg.app

import ch.addere.mdg.application.Import
import ch.addere.mdg.domain.model.Project
import ch.addere.mdg.domain.model.exporter.mermaid.MermaidFullGraphExporter
import ch.addere.mdg.domain.model.writer.ConsoleWriter
import ch.addere.mdg.domain.service.DependencyService
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.help
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.help
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.parameter.parametersOf
import java.io.File

class Dga : CliktCommand(help = "Analyse the dependency graph of a Gradle project."),
    KoinComponent {

    private val settingsFile: File by argument()
        .file()
        .help("Gradle settings file or parent folder containing one")

    private val mermaidGraph: Boolean by option("--mermaid-graph")
        .flag()
        .help("Generate Mermaid graph.")

    private val import: Import by inject()

    override fun run() {
        val project: Project = get { parametersOf(settingsFile) }
        val dag = import.readProject(project)
        val service: DependencyService = get { parametersOf(dag) }

        val analysedFileName = project.settingsFile.settingsFile.name
        val nofModules = service.allModules().size
        val nofDependencies = service.allDependencies().size
        val nofUniqueDependencies =
            service.allDependencies().map { it.configuration.name }.toSet().size

        echo("Analyse $analysedFileName")
        echo(String.format("%6d modules", nofModules))
        echo(String.format("%6d dependencies (%d unique)", nofDependencies, nofUniqueDependencies))

        if (mermaidGraph) {
            val mermaidFullGraphExporter = MermaidFullGraphExporter(service.allDependencies())
            mermaidFullGraphExporter.print(ConsoleWriter(::echo))
        }
    }
}

fun main(args: Array<String>) {

    startKoin {
        modules(dga)
    }

    Dga().main(args)
}
