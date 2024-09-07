package ch.addere.dga.app.infrastructure.factory

import ch.addere.dga.core.application.service.DependencySearchService
import ch.addere.dga.core.application.service.DependencySearchServiceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val coreModule = module {
    singleOf(::DependencySearchServiceImpl) { bind<DependencySearchService>() }
}
