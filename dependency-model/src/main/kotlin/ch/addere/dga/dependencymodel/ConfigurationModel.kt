package ch.addere.dga.dependencymodel

interface ConfigurationModel {

    val configuration: String
    val dependencyString: Dependency
    val destination: ProjectModule
}
