package ch.addere.mdg.app

import ch.addere.mdg.application.Import
import ch.addere.mdg.application.ImportImpl
import ch.addere.mdg.domain.model.Project
import ch.addere.mdg.domain.service.DependencyService
import ch.addere.mdg.domain.service.DependencyServiceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dga = module {
    singleOf(::DependencyServiceImpl) { bind<DependencyService>() }
    singleOf(::ImportImpl) { bind<Import>() }
    singleOf(::Project)
}
