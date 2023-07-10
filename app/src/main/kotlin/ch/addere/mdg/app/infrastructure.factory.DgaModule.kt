package ch.addere.mdg.app

import ch.addere.mdg.graph.domain.service.DependencyService
import ch.addere.mdg.graph.domain.service.DependencyServiceImpl
import ch.addere.mdg.importer.application.Import
import ch.addere.mdg.importer.application.ImportImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dgaModule = module {
    singleOf(::DependencyServiceImpl) { bind<DependencyService>() }
    singleOf(::ImportImpl) { bind<Import>() }
}
