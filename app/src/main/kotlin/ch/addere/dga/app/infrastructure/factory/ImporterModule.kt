package ch.addere.dga.app.infrastructure.factory

import ch.addere.dga.importer.application.service.GradleProjectLoader
import ch.addere.dga.importer.application.service.GradleProjectLoaderImpl
import ch.addere.dga.importer.domain.service.GradleConnectorService
import ch.addere.dga.importer.domain.service.ModelImporterService
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val importerModule = module {
    singleOf(::GradleConnectorService)
    singleOf(::ModelImporterService)
    singleOf(::GradleProjectLoaderImpl) { bind<GradleProjectLoader>() }
}
