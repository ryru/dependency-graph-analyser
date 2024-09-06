package ch.addere.dga.app.infrastructure.factory

import ch.addere.dga.app.domain.service.DependencyCommand
import ch.addere.dga.app.domain.service.OverviewService
import ch.addere.dga.app.domain.service.printer.ConsolePrinter
import ch.addere.dga.app.domain.service.printer.DependencyPrinter
import ch.addere.dga.app.domain.service.printer.MermaidPrinter
import ch.addere.dga.app.domain.service.printer.ModulePrinter
import ch.addere.dga.app.domain.service.printer.OverviewPrinter
import ch.addere.dga.core.domain.model.graph.ModuleDependencyDag
import ch.addere.dga.core.domain.service.ConfigurationService
import ch.addere.dga.core.domain.service.ConfigurationServiceImpl
import ch.addere.dga.core.domain.service.DependencyRelationService
import ch.addere.dga.core.domain.service.DependencyRelationServiceImpl
import ch.addere.dga.core.domain.service.DependencyRepository
import ch.addere.dga.core.domain.service.DependencyService
import ch.addere.dga.core.domain.service.DependencyServiceImpl
import ch.addere.dga.core.domain.service.ModuleRepository
import ch.addere.dga.core.domain.service.ModuleService
import ch.addere.dga.core.domain.service.ModuleServiceImpl
import ch.addere.dga.importer.application.service.GradleConnectorService
import ch.addere.dga.importer.domain.model.Project
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dgaModule = module {
    singleOf(::ConfigurationServiceImpl) { bind<ConfigurationService>() }
    singleOf(::ConsolePrinter)
    singleOf(::DependencyCommand)
    singleOf(::DependencyPrinter)
    singleOf(::DependencyRelationServiceImpl) { bind<DependencyRelationService>() }
    singleOf(::DependencyRepository)
    singleOf(::DependencyServiceImpl) { bind<DependencyService>() }
    singleOf(::GradleConnectorService)
    singleOf(::MermaidPrinter)
    singleOf(::ModuleDependencyDag)
    singleOf(::ModulePrinter)
    singleOf(::ModuleRepository)
    singleOf(::ModuleServiceImpl) { bind<ModuleService>() }
    singleOf(::OverviewPrinter)
    singleOf(::OverviewService)
    singleOf(::Project)
}
