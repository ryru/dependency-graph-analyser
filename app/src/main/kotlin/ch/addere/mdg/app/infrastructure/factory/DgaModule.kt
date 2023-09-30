package ch.addere.mdg.app.infrastructure.factory

import ch.addere.mdg.app.domain.model.CommandArgument
import ch.addere.mdg.app.domain.model.ConsolePrinter
import ch.addere.mdg.app.domain.model.DependencyPrinter
import ch.addere.mdg.app.domain.model.MermaidPrinter
import ch.addere.mdg.app.domain.model.ModulePrinter
import ch.addere.mdg.app.domain.model.OverviewPrinter
import ch.addere.mdg.app.domain.service.DependencyCommand
import ch.addere.mdg.graph.application.DependencyService
import ch.addere.mdg.graph.application.DependencyServiceImpl
import ch.addere.mdg.graph.application.ModuleService
import ch.addere.mdg.graph.application.ModuleServiceImpl
import ch.addere.mdg.graph.domain.model.graph.ModuleDependencyDag
import ch.addere.mdg.graph.domain.service.DependencyRelationService
import ch.addere.mdg.graph.domain.service.DependencyRelationServiceImpl
import ch.addere.mdg.graph.domain.service.DependencyRepository
import ch.addere.mdg.graph.domain.service.ModuleRepository
import ch.addere.mdg.importer.domain.model.Project
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dgaModule = module {
    singleOf(::ConsolePrinter)
    singleOf(::DependencyCommand)
    singleOf(::DependencyPrinter)
    singleOf(::DependencyRelationServiceImpl) { bind<DependencyRelationService>() }
    singleOf(::DependencyRepository)
    singleOf(::DependencyServiceImpl) { bind<DependencyService>() }
    singleOf(::MermaidPrinter)
    singleOf(::ModuleDependencyDag)
    singleOf(::ModulePrinter)
    singleOf(::ModuleRepository)
    singleOf(::ModuleServiceImpl) { bind<ModuleService>() }
    singleOf(::OverviewPrinter)
    singleOf(::project)
}

private fun project(
    argument: CommandArgument,
    moduleRepository: ModuleRepository,
    dependencyRepository: DependencyRepository
): Project {
    return Project(argument.settings, moduleRepository, dependencyRepository)
}