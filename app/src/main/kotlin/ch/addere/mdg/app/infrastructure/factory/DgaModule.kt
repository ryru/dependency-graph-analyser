package ch.addere.mdg.app.infrastructure.factory

import ch.addere.mdg.app.domain.model.CommandArgument
import ch.addere.mdg.app.domain.model.ConsolePrinter
import ch.addere.mdg.app.domain.model.MermaidPrinter
import ch.addere.mdg.app.domain.model.OverviewPrinter
import ch.addere.mdg.app.domain.service.DependencyCommand
import ch.addere.mdg.graph.application.module.ModuleService
import ch.addere.mdg.graph.application.module.ModuleServiceImpl
import ch.addere.mdg.graph.domain.model.graph.ModuleDependencyDag
import ch.addere.mdg.graph.domain.service.DependencyService
import ch.addere.mdg.graph.domain.service.DependencyServiceImpl
import ch.addere.mdg.graph.domain.service.ModuleRepository
import ch.addere.mdg.importer.domain.model.Project
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dgaModule = module {
    singleOf(::ConsolePrinter)
    singleOf(::DependencyCommand)
    singleOf(::DependencyServiceImpl) { bind<DependencyService>() }
    singleOf(::MermaidPrinter)
    singleOf(::ModuleRepository)
    singleOf(::ModuleServiceImpl) { bind<ModuleService>() }
    singleOf(::OverviewPrinter)
    singleOf(::dag)
    singleOf(::project)
}

private fun dag(moduleRepository: ModuleRepository, project: Project): ModuleDependencyDag {
    val dag = ModuleDependencyDag(moduleRepository)
    project.buildFiles.flatMap { it.dependencies }.forEach { dag.addDependency(it) }
    return dag
}

private fun project(argument: CommandArgument, moduleRepository: ModuleRepository): Project {
    return Project(argument.settings, moduleRepository)
}
