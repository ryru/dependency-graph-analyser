package ch.addere.dga.connectormodel

interface ConfigurationModel {

    val configuration: String
    val dependencyString: Dependency
    val destination: ProjectModule
}
