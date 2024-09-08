package ch.addere.dga.connectormodel

interface DependencyModel {

    /**
     * Return the name of the gradle project.
     */
    val projectName: String

    /**
     * Returns all module of a gradle project including the root project module.
     */
    val projectModules: Set<ProjectModule>

    /**
     * Return list of project module dependencies of each module.
     */
    val projectModuleDependencies: Map<String, List<ConfigurationModel>>
}
