package ch.addere.dga.app.infrastructure.factory

import ch.addere.dga.app.domain.service.DependencyCommandHandler
import ch.addere.dga.app.domain.service.FilterService
import ch.addere.dga.app.domain.service.ProjectFilterPrinter
import ch.addere.dga.app.domain.service.ProjectOutputPrinter
import ch.addere.dga.app.domain.service.ProjectOverviewPrinter
import ch.addere.dga.app.domain.service.printer.ConsolePrinter
import ch.addere.dga.app.domain.service.printer.DependencyPrinter
import ch.addere.dga.app.domain.service.printer.MermaidPrinter
import ch.addere.dga.app.domain.service.printer.ModulePrinter
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
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dgaModule = module {
    singleOf(::ConfigurationServiceImpl) { bind<ConfigurationService>() }
    singleOf(::ConsolePrinter)
    singleOf(::DependencyCommandHandler)
    singleOf(::DependencyPrinter)
    singleOf(::DependencyRelationServiceImpl) { bind<DependencyRelationService>() }
    singleOf(::DependencyRepository)
    singleOf(::DependencyServiceImpl) { bind<DependencyService>() }
    singleOf(::MermaidPrinter)
    singleOf(::ModuleDependencyDag)
    singleOf(::ModulePrinter)
    singleOf(::ModuleRepository)
    singleOf(::ModuleServiceImpl) { bind<ModuleService>() }
    singleOf(::ProjectOverviewPrinter)
    singleOf(::FilterService)
    singleOf(::ProjectFilterPrinter)
    singleOf(::ProjectOutputPrinter)
}
