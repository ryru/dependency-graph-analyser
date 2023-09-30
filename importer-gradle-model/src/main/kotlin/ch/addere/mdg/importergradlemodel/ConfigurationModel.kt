package ch.addere.mdg.importergradlemodel

interface ConfigurationModel {

    val configuration: String
    val dependencyString: Dependency
    val destination: ProjectModule
}
