package ch.addere.mdg.app.infrastructure.factory

import ch.addere.mdg.app.domain.model.CommandArgument
import ch.addere.mdg.app.domain.model.ConsolePrinter
import ch.addere.mdg.app.domain.model.MermaidPrinter
import ch.addere.mdg.app.domain.model.OverviewPrinter
import ch.addere.mdg.app.domain.service.DependencyCommand
import ch.addere.mdg.graph.domain.model.graph.ModuleDependencyDag
import ch.addere.mdg.graph.domain.service.DependencyService
import ch.addere.mdg.graph.domain.service.DependencyServiceImpl
import ch.addere.mdg.importer.application.Import
import ch.addere.mdg.importer.application.ImportImpl
import ch.addere.mdg.importer.domain.model.Project
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dgaModule = module {
    singleOf(::ConsolePrinter)
    singleOf(::DependencyCommand)
    singleOf(::DependencyServiceImpl) { bind<DependencyService>() }
    singleOf(::ImportImpl) { bind<Import>() }
    singleOf(::MermaidPrinter)
    singleOf(::OverviewPrinter)
    singleOf(::dag)
    singleOf(::project)
}

private fun dag(import: Import, project: Project): ModuleDependencyDag {
    return import.readProject(project)
}

private fun project(argument: CommandArgument): Project {
    return Project(argument.settings)
}
