package ch.addere.dga.app.infrastructure.factory

import ch.addere.dga.app.domain.model.ConsolePrinter
import ch.addere.dga.app.domain.model.DependencyPrinter
import ch.addere.dga.app.domain.model.MermaidPrinter
import ch.addere.dga.app.domain.model.ModulePrinter
import ch.addere.dga.app.domain.model.OverviewPrinter
import ch.addere.dga.app.domain.service.DependencyCommand
import ch.addere.dga.graph.application.DependencyService
import ch.addere.dga.graph.application.DependencyServiceImpl
import ch.addere.dga.graph.application.ModuleService
import ch.addere.dga.graph.application.ModuleServiceImpl
import ch.addere.dga.graph.domain.model.graph.ModuleDependencyDag
import ch.addere.dga.graph.domain.service.DependencyRelationService
import ch.addere.dga.graph.domain.service.DependencyRelationServiceImpl
import ch.addere.dga.graph.domain.service.DependencyRepository
import ch.addere.dga.graph.domain.service.ModuleRepository
import ch.addere.dga.importer.application.service.GradleConnectorService
import ch.addere.dga.importer.domain.model.Project
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
    singleOf(::GradleConnectorService)
    singleOf(::Project)
}
